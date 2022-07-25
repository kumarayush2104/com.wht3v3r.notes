package com.wht3v3r.notes

import java.time.LocalDateTime

class Note(title: String, content: String, date: String) {
    var noteTitle: String
    var noteContent: String
    var noteDate: String

    init {
        this.noteTitle = title
        this.noteContent = content
        this.noteDate = date
    }
}