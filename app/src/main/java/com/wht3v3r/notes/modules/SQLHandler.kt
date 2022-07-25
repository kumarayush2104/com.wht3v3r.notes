package com.wht3v3r.notes.modules

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.wht3v3r.notes.Note
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SQLHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "myDatabase"
        private const val TB_NAME = "myNotes"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("Create table if not exists myNotes(title var_char(20) not null, content var_char(600) not null, date Datetime not null )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun update(title: String, content: String, date: LocalDateTime) {
        val db = this.writableDatabase

        db.execSQL(
            "INSERT INTO $TB_NAME VALUES(\"$title\",\"$content\",\"$date\")"
        )
    }

    fun delete(content: String) {
        val db = this.writableDatabase

        db.execSQL(
            "Delete from $TB_NAME where content=\"$content\""
        )
    }

    override fun close() {
        val db = this.writableDatabase
        db.close()
    }

    fun fetch(array: ArrayList<Note>) {
        val cursor = this.writableDatabase.rawQuery("Select * from myNotes", null)
        cursor.moveToFirst()

        if (cursor.count == 0) {
            return
        } else {

            array.add(
                Note(
                    cursor.getString(0),
                    cursor.getString(1),
                    LocalDateTime.parse(cursor.getString(2)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                )
            )

            for (i in 0 until cursor.count - 1) {
                cursor.moveToNext()
                array.add(
                    Note(
                        cursor.getString(0),
                        cursor.getString(1),
                        LocalDateTime.parse(cursor.getString(2)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                    )
                )
            }
        }
        cursor.close()
    }

    fun edit(title: String, content: String, oldVersion: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE " + TB_NAME  + " SET title=" + "\"" + title + "\"," + " content=" + "\"" + content + "\"" + " WHERE content=" + "\"" + oldVersion + "\"")
    }
}