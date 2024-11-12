package com.example.pregunta4_login.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProfileImageDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ProfileImage.db"
        private const val TABLE_NAME = "profile_image"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE_PATH = "image_path"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_IMAGE_PATH TEXT"
                + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveImagePath(imagePath: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_IMAGE_PATH, imagePath)
        }

        val count = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf("1"))
        if (count == 0) {
            contentValues.put(COLUMN_ID, 1)
            db.insert(TABLE_NAME, null, contentValues)
        }
        db.close()
    }

    @SuppressLint("Range")
    fun getImagePath(): String? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_IMAGE_PATH), "$COLUMN_ID = ?", arrayOf("1"), null, null, null)

        var imagePath: String? = null

        if (cursor.moveToFirst()) {
            imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH))
        }
        cursor.close()
        db.close()
        return imagePath
    }
}