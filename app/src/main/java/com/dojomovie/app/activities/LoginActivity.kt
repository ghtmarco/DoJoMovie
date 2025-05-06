package com.dojomovie.app.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.ActivityLoginBinding
import com.dojomovie.app.utils.NetworkUtils
import com.dojomovie.app.utils.SessionManager
import com.dojomovie.app.utils.ValidationUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHelper: DatabaseHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        databaseHelper = DatabaseHelper(this)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Set input types
        binding.etPhoneNumber.inputType = InputType.TYPE_CLASS_PHONE
        binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        
        // Create clickable span for register text
        val text = "Didn't have an account? Register here!"
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = getColor(R.color.primary)
            }
        }
        
        val startIndex = text.indexOf("Register here!")
        val endIndex = startIndex + "Register here!".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        
        binding.tvRegister.text = spannableString
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }
    
    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            handleLogin()
        }
    }
    
    private fun handleLogin() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        // Validate phone number
        if (phoneNumber.isEmpty()) {
            showToast("Phone number must be filled")
            return
        }
        
        // Validate password
        if (password.isEmpty()) {
            showToast("Password must be filled")
            return
        }
        
        // Check if phone number is valid
        if (!ValidationUtils.isValidPhoneNumber(phoneNumber)) {
            showToast("Please enter a valid phone number")
            return
        }
        
        // Check if user exists in database
        val user = databaseHelper.getUserByPhoneNumber(phoneNumber)
        if (user == null) {
            showToast("User not found")
            return
        }
        
        // Check if password matches
        if (user.password != password) {
            showToast("Incorrect password")
            return
        }
        
        // Check if user is verified
        if (!user.isVerified) {
            // If not verified, save temp data and go to OTP page
            sessionManager.saveTempRegistrationData(phoneNumber, password)
            sendOTP(phoneNumber)
            return
        }
        
        // Login successful
        sessionManager.createLoginSession(user.id, user.phoneNumber)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun sendOTP(phoneNumber: String) {
        val otpCode = ValidationUtils.generateOTPCode()
        val message = "Your DoJo Movie verification code is: $otpCode"
        
        if (NetworkUtils.sendSMS(phoneNumber, message)) {
            // Update OTP in database
            val user = databaseHelper.getUserByPhoneNumber(phoneNumber)
            user?.let {
                val updatedUser = it.copy(otpCode = otpCode)
                // Update user with new OTP
                val success = databaseHelper.insertUser(updatedUser) > 0
                if (success) {
                    val intent = Intent(this, OTPActivity::class.java)
                    intent.putExtra("phone_number", phoneNumber)
                    startActivity(intent)
                } else {
                    showToast("Failed to send OTP")
                }
            }
        } else {
            showToast("Failed to send SMS")
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}