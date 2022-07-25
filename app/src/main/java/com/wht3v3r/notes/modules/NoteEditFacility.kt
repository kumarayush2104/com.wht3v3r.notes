package com.wht3v3r.notes.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.wht3v3r.notes.R
import java.time.LocalDate
import java.time.LocalDateTime

class NoteEditFacility : AppCompatActivity() {

    private lateinit var activityTitle: TextView
    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_edit_facility)

        activityTitle = findViewById(R.id.title)
        noteTitle = findViewById(R.id.noteTitle)
        noteContent = findViewById(R.id.noteContent)
        submit = findViewById(R.id.submitButton)

        if(intent.getStringExtra("Mode").toString() == "Edit") {

            activityTitle.text = "Edit a note"
            noteTitle.setText(intent.getStringExtra("title"))

            val oldVersion = intent.getStringExtra("content").toString()
            noteContent.setText(oldVersion)


            submit.setOnClickListener {
                SQLHandler(applicationContext).edit(noteTitle.text.toString(), noteContent.text.toString(), oldVersion)
                finish()
                Toast.makeText(applicationContext, "Note Modified Successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            submit.setOnClickListener {
                if(noteTitle.text.toString().isEmpty()) Toast.makeText(applicationContext, "Enter a Title", Toast.LENGTH_SHORT).show()
                else if(noteContent.text.toString().isEmpty()) Toast.makeText(applicationContext, "How are we supposed to save an empty note ??", Toast.LENGTH_SHORT).show()

                else {
                    SQLHandler(applicationContext).update(noteTitle.text.toString(), noteContent.text.toString(), LocalDateTime.now())
                    finish()
                    Toast.makeText(applicationContext, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}