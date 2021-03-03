package yoavsabag.easynote.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import yoavsabag.easynote.R
import yoavsabag.easynote.database.NoteDatabase
import yoavsabag.easynote.databinding.FragmentNotesBinding

class NoteListFragment : Fragment() {

    private lateinit var noteListViewModel : NoteListViewModel
    private lateinit var adapter : NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNotesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notes, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = NoteListViewModelFactory(dataSource, application)
        noteListViewModel = ViewModelProvider(this, viewModelFactory).get(NoteListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.noteListViewModel = noteListViewModel

        noteListViewModel.navigationToAddNewNote.observe(viewLifecycleOwner, Observer {
            note ->
            note?.let {
                this.findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragmentToAddNewNoteFragment(note))
                noteListViewModel.doneNavigation()
            }
        })

        adapter = NoteListAdapter(NoteListener { noteId ->
            noteListViewModel.onNoteClicked(noteId)
        })

        binding.noteList.adapter = adapter

        noteListViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        initSwipeNoteToDelete(binding)

        return binding.root
    }

    private fun initSwipeNoteToDelete(binding: FragmentNotesBinding) {
        var touchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val noteToDelete = adapter.currentList.get(position)

                noteListViewModel.deleteNote(noteToDelete)
            }
        }

        ItemTouchHelper(touchHelperCallback).apply { attachToRecyclerView(binding.noteList) }
    }

}