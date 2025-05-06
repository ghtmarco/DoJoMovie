package com.dojomovie.app.utils

import android.util.Patterns

object ValidationUtils {
    
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Basic phone number validation (Indonesian format)
        return phoneNumber.isNotBlank() && 
               phoneNumber.matches(Regex("^(\\+62|62|0)[0-9]{9,11}$"))
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
    
    fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
    
    fun isOTPValid(otp: String): Boolean {
        return otp.isNotBlank() && otp.length == 6 && otp.all { it.isDigit() }
    }
    
    fun isQuantityValid(quantity: String): Boolean {
        return try {
            val qty = quantity.toInt()
            qty > 0
        } catch (e: NumberFormatException) {
            false
        }
    }
    
    fun generateOTPCode(): String {
        return (100000..999999).random().toString()
    }
}