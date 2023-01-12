package com.candra.latihanroomdatabase

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.customview.widget.ViewDragHelper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.latihanroomdatabase.databinding.ItemNoteBinding
import com.candra.latihanroomdatabase.entity.Note
import com.candra.latihanroomdatabase.helper.NoteDiffCallback

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note){
            with(binding){
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context,ActitivityNoteAddUpdate::class.java).apply {
                        putExtra(ActitivityNoteAddUpdate.EXTRA_NOTE,note)
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }

    private val listNotes = ArrayList<Note>()

    fun setListNotes(listNotes: List<Note>){
        val diffCallback = NoteDiffCallback(this.listNotes,listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
       holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

}