package com.dojomovie.app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dojomovie.app.models.Film
import com.dojomovie.app.models.Transaction
import com.dojomovie.app.models.User
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_USERS)
        db.execSQL(DatabaseContract.SQL_CREATE_FILMS)
        db.execSQL(DatabaseContract.SQL_CREATE_TRANSACTIONS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseContract.SQL_DELETE_TRANSACTIONS)
        db.execSQL(DatabaseContract.SQL_DELETE_FILMS)
        db.execSQL(DatabaseContract.SQL_DELETE_USERS)
        onCreate(db)
    }

    // User operations
    fun insertUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UserEntry.COLUMN_NAME_PHONE_NUMBER, user.phoneNumber)
            put(DatabaseContract.UserEntry.COLUMN_NAME_PASSWORD, user.password)
            put(DatabaseContract.UserEntry.COLUMN_NAME_OTP_CODE, user.otpCode)
            put(DatabaseContract.UserEntry.COLUMN_NAME_IS_VERIFIED, if (user.isVerified) 1 else 0)
        }
        return db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values)
    }

    fun getUserByPhoneNumber(phoneNumber: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            DatabaseContract.UserEntry.TABLE_NAME,
            null,
            "${DatabaseContract.UserEntry.COLUMN_NAME_PHONE_NUMBER} = ?",
            arrayOf(phoneNumber),
            null, null, null
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry._ID)),
                phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME_PHONE_NUMBER)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME_PASSWORD)),
                otpCode = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME_OTP_CODE)),
                isVerified = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME_IS_VERIFIED)) == 1
            )
        }
        cursor.close()
        return user
    }

    fun updateUserVerification(phoneNumber: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UserEntry.COLUMN_NAME_IS_VERIFIED, 1)
        }
        return db.update(
            DatabaseContract.UserEntry.TABLE_NAME,
            values,
            "${DatabaseContract.UserEntry.COLUMN_NAME_PHONE_NUMBER} = ?",
            arrayOf(phoneNumber)
        )
    }

    // Film operations
    fun insertFilm(film: Film): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.FilmEntry._ID, film.id)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_TITLE, film.title)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_PRICE, film.price)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_COVER, film.cover)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_DESCRIPTION, film.description)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_GENRE, film.genre)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_DURATION, film.duration)
            put(DatabaseContract.FilmEntry.COLUMN_NAME_RATING, film.rating)
        }
        return db.insert(DatabaseContract.FilmEntry.TABLE_NAME, null, values)
    }

    fun getAllFilms(): List<Film> {
        val films = mutableListOf<Film>()
        val db = this.readableDatabase
        val cursor = db.query(DatabaseContract.FilmEntry.TABLE_NAME, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            films.add(
                Film(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry._ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_TITLE)),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_PRICE)),
                    cover = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_COVER)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_DESCRIPTION)),
                    genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_GENRE)),
                    duration = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_DURATION)),
                    rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_RATING))
                )
            )
        }
        cursor.close()
        return films
    }

    fun getFilmById(filmId: Int): Film? {
        val db = this.readableDatabase
        val cursor = db.query(
            DatabaseContract.FilmEntry.TABLE_NAME,
            null,
            "${DatabaseContract.FilmEntry._ID} = ?",
            arrayOf(filmId.toString()),
            null, null, null
        )

        var film: Film? = null
        if (cursor.moveToFirst()) {
            film = Film(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry._ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_TITLE)),
                price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_PRICE)),
                cover = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_COVER)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_DESCRIPTION)),
                genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_GENRE)),
                duration = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_DURATION)),
                rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_RATING))
            )
        }
        cursor.close()
        return film
    }

    // Transaction operations
    fun insertTransaction(transaction: Transaction): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TransactionEntry.COLUMN_NAME_USER_ID, transaction.userId)
            put(DatabaseContract.TransactionEntry.COLUMN_NAME_FILM_ID, transaction.filmId)
            put(DatabaseContract.TransactionEntry.COLUMN_NAME_QUANTITY, transaction.quantity)
            put(DatabaseContract.TransactionEntry.COLUMN_NAME_TOTAL_PRICE, transaction.totalPrice)
            put(DatabaseContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE, dateFormat.format(transaction.transactionDate))
        }
        return db.insert(DatabaseContract.TransactionEntry.TABLE_NAME, null, values)
    }

    fun getTransactionsByUserId(userId: Int): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val query = """
            SELECT t.*, f.${DatabaseContract.FilmEntry.COLUMN_NAME_TITLE}, f.${DatabaseContract.FilmEntry.COLUMN_NAME_PRICE}
            FROM ${DatabaseContract.TransactionEntry.TABLE_NAME} t
            INNER JOIN ${DatabaseContract.FilmEntry.TABLE_NAME} f 
            ON t.${DatabaseContract.TransactionEntry.COLUMN_NAME_FILM_ID} = f.${DatabaseContract.FilmEntry._ID}
            WHERE t.${DatabaseContract.TransactionEntry.COLUMN_NAME_USER_ID} = ?
            ORDER BY t.${DatabaseContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE} DESC
        """
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        while (cursor.moveToNext()) {
            transactions.add(
                Transaction(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry._ID)),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry.COLUMN_NAME_USER_ID)),
                    filmId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry.COLUMN_NAME_FILM_ID)),
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry.COLUMN_NAME_QUANTITY)),
                    totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry.COLUMN_NAME_TOTAL_PRICE)),
                    transactionDate = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE))) ?: Date(),
                    filmTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_TITLE)),
                    filmPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FilmEntry.COLUMN_NAME_PRICE))
                )
            )
        }
        cursor.close()
        return transactions
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DoJoMovie.db"
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }
}