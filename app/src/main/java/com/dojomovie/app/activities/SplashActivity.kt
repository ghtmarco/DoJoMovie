package com.dojomovie.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dojomovie.app.utils.SessionManager

class SplashActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        sessionManager = SessionManager(this)
        
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (sessionManager.isLoggedIn()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000) // 2 second delay
    }
}