package com.dojomovie.app.models

data class User(
    val id: Int = 0,
    val phoneNumber: String,
    val password: String,
    val otpCode: String? = null,
    val isVerified: Boolean = false
)