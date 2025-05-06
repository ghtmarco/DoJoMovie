package com.dojomovie.app.models

import java.util.Date

data class Transaction(
    val id: Int = 0,
    val userId: Int,
    val filmId: Int,
    val quantity: Int,
    val totalPrice: Double,
    val transactionDate: Date = Date(),
    val filmTitle: String? = null,
    val filmPrice: Double? = null
)