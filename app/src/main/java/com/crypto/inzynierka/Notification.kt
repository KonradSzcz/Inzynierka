package com.crypto.inzynierka

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crypto.inzynierka.databinding.FragmentNotificationBinding

class Notification : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var notificationAdapter: NotificationAdapter
    private var notificationList = mutableListOf<NotificationData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicjalizacja RecyclerView i adaptera
        notificationAdapter = NotificationAdapter(notificationList)
        binding.notifications.layoutManager = LinearLayoutManager(requireContext())
        binding.notifications.adapter = notificationAdapter

        // Inicjalizacja bazy danych
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)
        loadNotificationsFromDatabase()

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val notification = notificationList[position]

                // Usuwanie powiadomienia z bazy
                deleteNotificationFromDatabase(notification)

                // Usunięcie powiadomienia z listy
                notificationList.removeAt(position)

                // Odświeżenie całej listy
                notificationAdapter.notifyDataSetChanged()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.notifications)
    }

    // Funkcja do załadowania powiadomień z bazy danych
    private fun loadNotificationsFromDatabase() {
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT ID, Title, Content FROM Notifications ORDER BY ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0) // Pobierz ID powiadomienia
                val title = cursor.getString(1)
                val content = cursor.getString(2)
                notificationList.add(NotificationData(id, title, content)) // Dodanie powiadomienia do listy
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        notificationAdapter.notifyDataSetChanged()
    }

    private fun deleteNotificationFromDatabase(notification: NotificationData) {
        val db = dbHelper.writableDatabase

        db.delete("Notifications", "ID = ?", arrayOf(notification.id.toString()))

        db.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
