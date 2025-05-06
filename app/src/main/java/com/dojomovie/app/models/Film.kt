package com.dojomovie.app.models

data class Film(
    val id: Int,
    val title: String,
    val price: Double,
    val cover: String,
    val description: String? = null,
    val genre: String? = null,
    val duration: String? = null,
    val rating: Double? = null
)