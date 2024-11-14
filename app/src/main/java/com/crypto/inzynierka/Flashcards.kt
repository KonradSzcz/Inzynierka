package com.crypto.inzynierka

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.crypto.inzynierka.databinding.FragmentFlashcardsBinding

class Flashcards : Fragment() {
    private var _binding: FragmentFlashcardsBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private var flashcards = mutableListOf<Triple<String, String, Int>>()
    private lateinit var adapter: FlashcardAdapter
    private lateinit var dbHelper: DBConnection

    private var initialCardCount = 0
    private var rightSwipeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)
    }

    private fun loadFlashcardsFromDatabase(chapters: List<String>) {
        flashcards.clear()
        val db = dbHelper.readableDatabase
        val query = if (chapters.isEmpty()) {
            "SELECT * FROM ${DBConnection.TABLE_NAME_FLASHCARDS}"
        } else {
            "SELECT * FROM ${DBConnection.TABLE_NAME_FLASHCARDS} WHERE ${DBConnection.COL4_FLASHCARDS} IN (${chapters.joinToString { "'$it'" }})"
        }
        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val concept = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_FLASHCARDS))
                val definition = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL3_FLASHCARDS))
                val line = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL5_FLASHCARDS))
                flashcards.add(Triple(concept, definition, line))
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Update initial card count and reset right swipe count
        initialCardCount = flashcards.size
        rightSwipeCount = 0
        adapter.notifyDataSetChanged() // Refresh the adapter with new data
        updateCardCounter() // Update the card counter
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

        val checkBoxChapter1 = view.findViewById<CheckBox>(R.id.checkbox_chapter1)
        val checkBoxChapter2 = view.findViewById<CheckBox>(R.id.checkbox_chapter2)
        val checkBoxChapter3 = view.findViewById<CheckBox>(R.id.checkbox_chapter3)
        val checkBoxChapter4 = view.findViewById<CheckBox>(R.id.checkbox_chapter4)

        // Initial load of all flashcards
        loadFlashcardsFromDatabase(emptyList())

        // Update flashcards when any checkbox state changes
        val onChapterSelectionChanged = {
            val selectedChapters = mutableListOf<String>()
            if (checkBoxChapter1.isChecked) selectedChapters.add("chapter1")
            if (checkBoxChapter2.isChecked) selectedChapters.add("chapter2")
            if (checkBoxChapter3.isChecked) selectedChapters.add("chapter3")
            if (checkBoxChapter4.isChecked) selectedChapters.add("chapter4")

            loadFlashcardsFromDatabase(selectedChapters)
        }

        checkBoxChapter1.setOnCheckedChangeListener { _, _ -> onChapterSelectionChanged() }
        checkBoxChapter2.setOnCheckedChangeListener { _, _ -> onChapterSelectionChanged() }
        checkBoxChapter3.setOnCheckedChangeListener { _, _ -> onChapterSelectionChanged() }
        checkBoxChapter4.setOnCheckedChangeListener { _, _ -> onChapterSelectionChanged() }

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

                if (rightSwipeCount == initialCardCount) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isAdded) {
                            replaceFragment(Home())
                        }
                    }, 5000)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        updateCardCounter()
    }

    private fun updateFlashcardLine(position: Int, newLine: Int) {
        val flashcard = flashcards[position]
        val updatedFlashcard = flashcard.copy(third = newLine)
        flashcards[position] = updatedFlashcard
    }

    private fun updateCardCounter() {
        binding.cardCounter.text = "$rightSwipeCount / $initialCardCount"
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
