package com.candra.latihansqlitedatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.latihansqlitedatabase.databinding.ItemNoteBinding
import com.candra.latihansqlitedatabase.entity.Note

class NoteAdapter(private val onItemCallback: OnItemClickCallback): RecyclerView.Adapter<NoteAdapter.ViewHolder>()
{

    var listNotes = ArrayList<Note>()
        set(listNotes){
            if (listNotes.size > 0){
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        holder.bind(note = listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(note: Note){
            with(binding){
                tvItemTitle.text = note.title
                tvItemDate.text  = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener{
                    onItemCallback.onItemClicked(note,adapterPosition)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?,position: Int?)
    }

    fun addItem(note: Note){
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int,note: Note){
        this.listNotes[position] = note
        notifyItemChanged(position,note)
    }

    fun removeItem(position: Int){
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,this.listNotes.size)
    }

}