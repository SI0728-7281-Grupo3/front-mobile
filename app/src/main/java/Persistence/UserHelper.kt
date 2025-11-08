package Persistence

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.restyle_mobile.Beans.User

class UserHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_IS_REMODELER = "is_remodeler"
        const val COLUMN_PHOTO_URL = "photo_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USER_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_FULL_NAME TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_PASSWORD TEXT,"
                + "$COLUMN_IS_REMODELER INTEGER,"
                + "$COLUMN_PHOTO_URL TEXT)")
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(fullName: String, email: String, password: String, isRemodeler: Boolean, photoUrl: String?): Boolean {
        val db = this.writableDatabase

        // Check if email already exists
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL=?", arrayOf(email))
        val emailExists = cursor.count > 0
        cursor.close()

        if (emailExists) {
            // Email is already in use
            return false
        }

        val contentValues = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_IS_REMODELER, if (isRemodeler) 1 else 0)
            put(COLUMN_PHOTO_URL, photoUrl)
        }

        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun checkUser(email: String, password: String): Pair<Boolean, Boolean?> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_IS_REMODELER FROM $TABLE_NAME WHERE $COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?",
            arrayOf(email, password)
        )

        return if (cursor.moveToFirst()) {
            val isRemodeler = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_REMODELER)) == 1
            cursor.close()
            Pair(true, isRemodeler)
        } else {
            cursor.close()
            Pair(false, null)
        }
    }

    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL=?", arrayOf(email))

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                isRemodeler = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_REMODELER)) == 1,
                photoUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO_URL))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun updateUser(email: String, fullName: String, photoUrl: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PHOTO_URL, photoUrl)
        }

        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_EMAIL=?", arrayOf(email))
        db.close()
        return result > 0
    }

    fun checkUserWithId(email: String, password: String): Triple<Boolean, Boolean?, String?> {
        val db = readableDatabase
        val query = "SELECT id, is_remodeler FROM users WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return if (cursor.moveToFirst()) {
            val userId = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val isRemodeler = cursor.getInt(cursor.getColumnIndexOrThrow("is_remodeler")) == 1
            Triple(true, isRemodeler, userId)
        } else {
            Triple(false, null, null)
        }.also {
            cursor.close()
        }
    }

    fun getUserDataById(userId: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=?", arrayOf(userId))

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                isRemodeler = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_REMODELER)) == 1,
                photoUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO_URL))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }


}
