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
import com.dojomovie.app.R
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.ActivityRegisterBinding
import com.dojomovie.app.utils.NetworkUtils
import com.dojomovie.app.utils.SessionManager
import com.dojomovie.app.utils.ValidationUtils

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHelper: DatabaseHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        binding.etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        
        // Create clickable span for login text
        val text = "Already has an account? Log in here"
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = getColor(R.color.primary)
            }
        }
        
        val startIndex = text.indexOf("Log in here")
        val endIndex = startIndex + "Log in here".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        
        binding.tvLogin.text = spannableString
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()
    }
    
    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            handleRegister()
        }
    }
    
    private fun handleRegister() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        
        // Validate all fields
        if (phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("All fields must be filled")
            return
        }
        
        // Validate phone number format
        if (!ValidationUtils.isValidPhoneNumber(phoneNumber)) {
            showToast("Please enter a valid phone number")
            return
        }
        
        // Check if phone number already exists
        val existingUser = databaseHelper.getUserByPhoneNumber(phoneNumber)
        if (existingUser != null) {
            showToast("Phone number already registered")
            return
        }
        
        // Validate password length
        if (!ValidationUtils.isValidPassword(password)) {
            showToast("Password must be at least 8 characters")
            return
        }
        
        // Validate password match
        if (!ValidationUtils.isPasswordMatch(password, confirmPassword)) {
            showToast("Passwords do not match")
            return
        }
        
        // Save temp registration data
        sessionManager.saveTempRegistrationData(phoneNumber, password)
        
        // Generate and send OTP
        sendOTP(phoneNumber)
    }
    
    private fun sendOTP(phoneNumber: String) {
        val otpCode = ValidationUtils.generateOTPCode()
        val message = "Your DoJo Movie verification code is: $otpCode"
        
        if (NetworkUtils.sendSMS(phoneNumber, message)) {
            // Navigate to OTP verification page
            val intent = Intent(this, OTPActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            intent.putExtra("otp_code", otpCode)
            intent.putExtra("is_register", true)
            startActivity(intent)
        } else {
            showToast("Failed to send SMS. Please try again.")
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}