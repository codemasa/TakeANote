package com.codemasa.codyabe.takeanote

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


class NoteListAdapter(private val context: Context, private val messageList: List<Note>) : RecyclerView.Adapter<NoteHolder>()
{

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteHolder? {
        val view = inflater.inflate(R.layout.list_item_note_added, parent, false)

        return NoteHolder(context,view)


    }

    override fun getItemCount(): Int {
        return messageList.count()
    }

    override fun onBindViewHolder(holder: NoteHolder?, position: Int) {
        val message = messageList.get(position)

        holder?.bind(message)


    }

}