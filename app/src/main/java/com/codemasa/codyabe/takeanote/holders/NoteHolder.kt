package com.codemasa.codyabe.takeanote.holders

import android.content.Context
import android.text.format.DateUtils.formatDateTime
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.codemasa.codyabe.takeanote.R
import com.codemasa.codyabe.takeanote.model.Note


class NoteHolder internal constructor(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var messageText: TextView
    internal var timeText: TextView

    init {
        messageText = itemView.findViewById(R.id.text_message_body)
        timeText = itemView.findViewById(R.id.text_message_time)
    }

    internal fun bind(note: Note) {
        messageText.setText(note.noteBody)

        // Format the stored timestamp into a readable String using method.
        timeText.setText(formatDateTime(this.context,note.createdAt,0))

    }
}