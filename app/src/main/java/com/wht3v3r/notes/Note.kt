package com.wht3v3r.notes

import java.time.LocalDate

class Note(title: String, content: String, date: LocalDate) {
    var noteTitle: String
    var noteContent: String
    var noteDate: LocalDate

    init {
        this.noteTitle = title
        this.noteContent = content
        this.noteDate = date
    }
}