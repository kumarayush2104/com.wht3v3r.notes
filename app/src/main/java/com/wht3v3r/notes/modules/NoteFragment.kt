package com.wht3v3r.notes.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wht3v3r.notes.R
import java.time.LocalDate

class NoteFragment(
    private var noteTitle: String,
    private var noteDate: LocalDate,
    private var noteContent: String
    ) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val inflate = inflater.inflate(R.layout.note_fragment, null)

        inflate.findViewById<TextView>(R.id.noteTitle).text = noteTitle
        inflate.findViewById<TextView>(R.id.noteContent).text = noteContent
        inflate.findViewById<TextView>(R.id.noteDate).text = noteDate.toString()

        return inflate
    }
}