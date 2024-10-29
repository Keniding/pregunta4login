package com.example.pregunta4_login.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pregunta4_login.models.Personal

class PersonalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "malva_lite.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_PERSONAL = "personal"
        const val COLUMN_DNI = "dni"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_FOTO = "foto"
        const val COLUMN_ID_ROL = "id_rol"
        const val COLUMN_FECHA_CREACION = "fecha_creacion"
        const val COLUMN_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PERSONAL (
                $COLUMN_DNI INTEGER PRIMARY KEY,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_FOTO TEXT,
                $COLUMN_ID_ROL INTEGER,
                $COLUMN_FECHA_CREACION TEXT,
                $COLUMN_ESTADO INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAL")
        onCreate(db)
    }

    // Método para agregar un objeto Personal
    fun addPersonal(personal: Personal) {
        val db = writableDatabase

        // Verifica si el DNI ya existe
        val cursor = db.query(
            TABLE_PERSONAL,
            arrayOf(COLUMN_DNI),
            "$COLUMN_DNI = ?",
            arrayOf(personal.dni.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            // El DNI ya existe, puedes optar por actualizar o ignorar
            Log.e("DatabaseHelper", "El registro con DNI ${personal.dni} ya existe.")
        } else {
            // Inserta el nuevo registro
            val values = ContentValues().apply {
                put(COLUMN_DNI, personal.dni)
                put(COLUMN_NOMBRE, personal.nombre)
                put(COLUMN_EMAIL, personal.email)
                put(COLUMN_FOTO, personal.foto)
                put(COLUMN_ID_ROL, personal.id_rol)
                put(COLUMN_FECHA_CREACION, personal.fecha_creacion)
                put(COLUMN_ESTADO, personal.estado)
            }
            db.insert(TABLE_PERSONAL, null, values)
        }
        cursor.close()
    }


    // Método para obtener todos los objetos Personal
    @SuppressLint("Range")
    fun getAllPersonal(): List<Personal> {
        val personalList = mutableListOf<Personal>()
        val db = readableDatabase
        val cursor = db.query(TABLE_PERSONAL, null, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val dni = cursor.getInt(cursor.getColumnIndex(COLUMN_DNI))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)) ?: "Desconocido"
                val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)) ?: "noemail@ejemplo.com"
                val foto = cursor.getString(cursor.getColumnIndex(COLUMN_FOTO)) ?: ""
                val idRol = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ROL))
                val fechaCreacion = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_CREACION)) ?: "Fecha no disponible"
                val estado = cursor.getInt(cursor.getColumnIndex(COLUMN_ESTADO))

                val personal = Personal(dni, nombre, email, foto, idRol, fechaCreacion, estado)
                personalList.add(personal)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return personalList
    }
}
