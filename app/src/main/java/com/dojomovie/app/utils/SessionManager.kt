package com.dojomovie.app.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "DoJoMovieSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
        private const val KEY_PHONE_NUMBER = "phoneNumber"
        private const val KEY_TEMP_PHONE_NUMBER = "tempPhoneNumber"
        private const val KEY_TEMP_PASSWORD = "tempPassword"
    }
    
    fun createLoginSession(userId: Int, phoneNumber: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_PHONE_NUMBER, phoneNumber)
        editor.apply()
    }
    
    fun saveTempRegistrationData(phoneNumber: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TEMP_PHONE_NUMBER, phoneNumber)
        editor.putString(KEY_TEMP_PASSWORD, password)
        editor.apply()
    }
    
    fun getTempRegistrationData(): Pair<String?, String?> {
        val phoneNumber = sharedPreferences.getString(KEY_TEMP_PHONE_NUMBER, null)
        val password = sharedPreferences.getString(KEY_TEMP_PASSWORD, null)
        return Pair(phoneNumber, password)
    }
    
    fun clearTempRegistrationData() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_TEMP_PHONE_NUMBER)
        editor.remove(KEY_TEMP_PASSWORD)
        editor.apply()
    }
    
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    
    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }
    
    fun getPhoneNumber(): String? {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null)
    }
    
    fun logOut() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}