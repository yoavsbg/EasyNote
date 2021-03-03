package yoavsabag.easynote.ui.addnewnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import yoavsabag.easynote.R
import yoavsabag.easynote.database.NoteDatabase
import yoavsabag.easynote.databinding.FragmentAddNewNoteBinding
import yoavsabag.easynote.databinding.FragmentNotesBinding
import yoavsabag.easynote.ui.notelist.NoteListViewModel
import yoavsabag.easynote.ui.notelist.NoteListViewModelFactory


class AddNewNoteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentAddNewNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_new_note, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = AddNewNoteFragmentArgs.fromBundle(requireArguments())
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = AddNewNoteModelFactory(arguments.noteKey, dataSource )
        val addNewNoteViewModel = ViewModelProvider(this, viewModelFactory).get(AddNewNoteViewModel::class.java)

        binding.lifecycleOwner = this
        binding.addNewNoteViewModel = addNewNoteViewModel


        addNewNoteViewModel.navigationToNoteList.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(AddNewNoteFragmentDirections.actionAddNewNoteFragmentToNoteListFragment())
                addNewNoteViewModel.doneNavigating()
            }
        })


        return binding.root

    }

}