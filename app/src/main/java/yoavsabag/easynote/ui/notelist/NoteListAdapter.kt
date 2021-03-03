package yoavsabag.easynote.ui.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yoavsabag.easynote.databinding.NoteItemBinding
import yoavsabag.easynote.model.Note

class NoteListAdapter(private val clickListener: NoteListener) : ListAdapter<Note, NoteListAdapter.ViewHolder>(NoteDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }




    class ViewHolder private constructor(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Note, clickListener: NoteListener) {
            binding.clickListener = clickListener
            binding.note = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

class NoteListener(val clickListener: (noteId:Long) -> Unit) {
    fun onClick(note: Note) = clickListener(note.noteId)
}