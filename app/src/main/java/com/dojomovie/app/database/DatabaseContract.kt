package com.dojomovie.app.database

import android.provider.BaseColumns

object DatabaseContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_PHONE_NUMBER = "phone_number"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_OTP_CODE = "otp_code"
        const val COLUMN_NAME_IS_VERIFIED = "is_verified"
    }

    object FilmEntry : BaseColumns {
        const val TABLE_NAME = "films"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_COVER = "cover"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_GENRE = "genre"
        const val COLUMN_NAME_DURATION = "duration"
        const val COLUMN_NAME_RATING = "rating"
    }

    object TransactionEntry : BaseColumns {
        const val TABLE_NAME = "transactions"
        const val COLUMN_NAME_USER_ID = "user_id"
        const val COLUMN_NAME_FILM_ID = "film_id"
        const val COLUMN_NAME_QUANTITY = "quantity"
        const val COLUMN_NAME_TOTAL_PRICE = "total_price"
        const val COLUMN_NAME_TRANSACTION_DATE = "transaction_date"
    }
    
    const val SQL_CREATE_USERS =
        "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
        "${UserEntry.COLUMN_NAME_PHONE_NUMBER} TEXT UNIQUE NOT NULL," +
        "${UserEntry.COLUMN_NAME_PASSWORD} TEXT NOT NULL," +
        "${UserEntry.COLUMN_NAME_OTP_CODE} TEXT," +
        "${UserEntry.COLUMN_NAME_IS_VERIFIED} INTEGER DEFAULT 0)"

    const val SQL_CREATE_FILMS =
        "CREATE TABLE ${FilmEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${FilmEntry.COLUMN_NAME_TITLE} TEXT NOT NULL," +
        "${FilmEntry.COLUMN_NAME_PRICE} REAL NOT NULL," +
        "${FilmEntry.COLUMN_NAME_COVER} TEXT," +
        "${FilmEntry.COLUMN_NAME_DESCRIPTION} TEXT," +
        "${FilmEntry.COLUMN_NAME_GENRE} TEXT," +
        "${FilmEntry.COLUMN_NAME_DURATION} TEXT," +
        "${FilmEntry.COLUMN_NAME_RATING} REAL)"

    const val SQL_CREATE_TRANSACTIONS =
        "CREATE TABLE ${TransactionEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
        "${TransactionEntry.COLUMN_NAME_USER_ID} INTEGER NOT NULL," +
        "${TransactionEntry.COLUMN_NAME_FILM_ID} INTEGER NOT NULL," +
        "${TransactionEntry.COLUMN_NAME_QUANTITY} INTEGER NOT NULL," +
        "${TransactionEntry.COLUMN_NAME_TOTAL_PRICE} REAL NOT NULL," +
        "${TransactionEntry.COLUMN_NAME_TRANSACTION_DATE} TEXT NOT NULL," +
        "FOREIGN KEY (${TransactionEntry.COLUMN_NAME_USER_ID}) REFERENCES ${UserEntry.TABLE_NAME}(${BaseColumns._ID})," +
        "FOREIGN KEY (${TransactionEntry.COLUMN_NAME_FILM_ID}) REFERENCES ${FilmEntry.TABLE_NAME}(${BaseColumns._ID}))"

    const val SQL_DELETE_USERS = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"
    const val SQL_DELETE_FILMS = "DROP TABLE IF EXISTS ${FilmEntry.TABLE_NAME}"
    const val SQL_DELETE_TRANSACTIONS = "DROP TABLE IF EXISTS ${TransactionEntry.TABLE_NAME}"
}