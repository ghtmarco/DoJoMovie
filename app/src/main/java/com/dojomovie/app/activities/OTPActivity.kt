package com.dojomovie.app.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.ActivityOtpBinding
import com.dojomovie.app.models.User
import com.dojomovie.app.utils.SessionManager
import com.dojomovie.app.utils.ValidationUtils

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHelper: DatabaseHelper
    private var phoneNumber: String = ""
    private var otpCode: String = ""
    private var isRegister: Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        databaseHelper = DatabaseHelper(this)
        
        getIntentExtras()
        setupUI()
        setupClickListeners()
    }
    
    private fun getIntentExtras() {
        phoneNumber = intent.getStringExtra("phone_number") ?: ""
        otpCode = intent.getStringExtra("otp_code") ?: ""
        isRegister = intent.getBooleanExtra("is_register", false)
    }
    
    private fun setupUI() {
        binding.etOtp.inputType = InputType.TYPE_CLASS_NUMBER
        binding.tvPhoneNumber.text = "Enter OTP sent to $phoneNumber"
    }
    
    private fun setupClickListeners() {
        binding.btnVerify.setOnClickListener {
            handleOTPVerification()
        }
    }
    
    private fun handleOTPVerification() {
        val inputOTP = binding.etOtp.text.toString().trim()
        
        // Validate OTP input
        if (inputOTP.isEmpty()) {
            showToast("OTP must be filled")
            return
        }
        
        if (!ValidationUtils.isOTPValid(inputOTP)) {
            showToast("Please enter a valid 6-digit OTP")
            return
        }
        
        // For registration flow
        if (isRegister) {
            if (inputOTP == otpCode) {
                createUserAccount()
            } else {
                showToast("Invalid OTP. Please try again.")
            }
        } else {
            // For login flow (when user is not verified)
            val user = databaseHelper.getUserByPhoneNumber(phoneNumber)
            if (user != null && user.otpCode == inputOTP) {
                // Update user verification status
                databaseHelper.updateUserVerification(phoneNumber)
                
                // Create login session
                sessionManager.createLoginSession(user.id, user.phoneNumber)
                
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid OTP. Please try again.")
            }
        }
    }
    
    private fun createUserAccount() {
        val (tempPhoneNumber, tempPassword) = sessionManager.getTempRegistrationData()
        
        if (tempPhoneNumber != null && tempPassword != null) {
            val user = User(
                phoneNumber = tempPhoneNumber,
                password = tempPassword,
                isVerified = true
            )
            
            val userId = databaseHelper.insertUser(user)
            if (userId > 0) {
                // Clear temp data
                sessionManager.clearTempRegistrationData()
                
                // Show success message and navigate to login
                showToast("Registration successful! Please login to continue.")
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                showToast("Registration failed. Please try again.")
            }
        } else {
            showToast("Registration data not found. Please try again.")
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}