package com.crypto.inzynierka

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.crypto.inzynierka.databinding.FragmentFlashcardsBinding

class Flashcards : Fragment() {
    private var _binding: FragmentFlashcardsBinding? = null
    private val binding get() = _binding!!

    private var flashcards = mutableListOf<Triple<String, String, Int>>()
    private lateinit var adapter: FlashcardAdapter
    private lateinit var dbHelper: DBConnection

    private var initialCardCount = 0
    private var rightSwipeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", 6)
        loadFlashcardsFromDatabase()
        initialCardCount = flashcards.size
    }

    private fun loadFlashcardsFromDatabase() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBConnection.TABLE_NAME_FLASHCARDS}", null)
        if (cursor.moveToFirst()) {
            do {
                val concept = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_FLASHCARDS))
                val definition = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL3_FLASHCARDS))
                val line = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL5_FLASHCARDS))
                flashcards.add(Triple(concept, definition, line))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlashcardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FlashcardAdapter(flashcards)
        binding.recyclerView.layoutManager = OverlayLayoutManager()
        binding.recyclerView.adapter = adapter

        updateCardCounter()

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (direction == ItemTouchHelper.RIGHT) {
                    updateFlashcardLine(position, 1)
                    flashcards.removeAt(position)
                    rightSwipeCount++
                    adapter.notifyItemRemoved(position)
                } else if (direction == ItemTouchHelper.LEFT) {
                    val flashcard = flashcards[position]
                    if (flashcard.third == 0) {
                        flashcards.removeAt(position)
                        flashcards.add(flashcard)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemInserted(flashcards.size - 1)
                    }
                }
                adapter.notifyDataSetChanged()
                updateCardCounter()

                // Check if all cards are swiped to the right
                if (rightSwipeCount == initialCardCount) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isAdded) {
                            replaceFragment(Introduction())
                        }
                    }, 5000)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun updateFlashcardLine(position: Int, newLine: Int) {
        val flashcard = flashcards[position]
        val updatedFlashcard = flashcard.copy(third = newLine)
        flashcards[position] = updatedFlashcard
    }

    private fun updateCardCounter() {
        binding.cardCounter.text = "$rightSwipeCount/$initialCardCount"
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
