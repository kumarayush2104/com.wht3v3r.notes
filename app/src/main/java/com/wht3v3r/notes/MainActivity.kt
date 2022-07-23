package com.wht3v3r.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wht3v3r.notes.modules.NoteEditFacility
import com.wht3v3r.notes.modules.NoteFragment
import com.wht3v3r.notes.modules.SQLHandler
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var notesList = arrayListOf<Note>()

    lateinit var noteListView: ListView
    private lateinit var newNote: FloatingActionButton
    private lateinit var bottomSheet: NoteFragment

    private var layoutInflateManager: LayoutInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteListView = findViewById(R.id.notesList)
        newNote = findViewById(R.id.newNote)
        layoutInflateManager = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        newNote.setOnClickListener {
            startActivity(Intent(applicationContext, NoteEditFacility::class.java))
        }

        noteListView.adapter = NoteListViewAdapter()
    }

    inner class NoteListViewAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return notesList.size
        }

        override fun getItem(p0: Int): Any {
            return notesList[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val inflate = layoutInflateManager!!.inflate(R.layout.notes_ticket, null)
            inflate.findViewById<TextView>(R.id.noteTitle).text = notesList[p0].noteTitle

            inflate.findViewById<Button>(R.id.delNote).setOnClickListener {
                SQLHandler(applicationContext).delete(notesList[p0].noteContent)
                notesList.clear()
                SQLHandler(applicationContext).fetch(notesList)

                noteListView.adapter = NoteListViewAdapter()

            }

            inflate.findViewById<Button>(R.id.editNote).setOnClickListener {
                val intent = Intent(applicationContext, NoteEditFacility::class.java).putExtra("Mode", "Edit").putExtra("title", notesList[p0].noteTitle).putExtra("content", notesList[p0].noteContent)
                startActivity(intent)
            }

            inflate.setOnClickListener {
                bottomSheet = NoteFragment(
                    notesList[p0].noteTitle,
                    notesList[p0].noteDate,
                    notesList[p0].noteContent
                )
                bottomSheet.show(supportFragmentManager, "NoteView")
            }
            return inflate
        }
    }

    override fun onResume() {
        super.onResume()
        notesList.clear()
        SQLHandler(applicationContext).fetch(notesList)
        noteListView.adapter = NoteListViewAdapter()
    }

    override fun onPause() {
        super.onPause()
        try {
            bottomSheet.dismiss()
        } catch (ex: Exception) {
            Log.i("ModalSheet", "Not able to find any open modal sheet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SQLHandler(applicationContext).close()
    }
}

