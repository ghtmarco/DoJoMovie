package com.dojomovie.app.activities

import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.ActivityDetailFilmBinding
import com.dojomovie.app.models.Film
import com.dojomovie.app.models.Transaction
import com.dojomovie.app.utils.SessionManager
import com.dojomovie.app.utils.ValidationUtils

class DetailFilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFilmBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private var film: Film? = null
    private var filmId: Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)
        
        getIntentExtras()
        setupUI()
        setupClickListeners()
        loadFilmDetails()
    }
    
    private fun getIntentExtras() {
        filmId = intent.getIntExtra("film_id", 0)
    }
    
    private fun setupUI() {
        // Set input type for quantity
        binding.etQuantity.inputType = InputType.TYPE_CLASS_NUMBER
        
        // Add text watcher to update total price
        binding.etQuantity.addTextChangedListener { text ->
            updateTotalPrice()
        }
        
        // Setup back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun setupClickListeners() {
        binding.btnBuy.setOnClickListener {
            handleBuyFilm()
        }
    }
    
    private fun loadFilmDetails() {
        film = databaseHelper.getFilmById(filmId)
        
        film?.let { f ->
            binding.apply {
                tvTitle.text = f.title
                tvPrice.text = "Rp ${String.format("%,.0f", f.price)}"
                tvDescription.text = f.description ?: "No description available"
                tvGenre.text = "Genre: ${f.genre ?: "N/A"}"
                tvDuration.text = "Duration: ${f.duration ?: "N/A"}"
                tvRating.text = "Rating: ${f.rating ?: 0.0}/10"
                
                // Load cover image
                Glide.with(this@DetailFilmActivity)
                    .load(f.cover)
                    .centerCrop()
                    .into(ivCover)
            }
        }
    }
    
    private fun updateTotalPrice() {
        val quantityText = binding.etQuantity.text.toString()
        if (quantityText.isNotEmpty() && ValidationUtils.isQuantityValid(quantityText)) {
            val quantity = quantityText.toInt()
            val totalPrice = quantity * (film?.price ?: 0.0)
            binding.tvTotalPrice.text = "Total Price: Rp ${String.format("%,.0f", totalPrice)}"
        } else {
            binding.tvTotalPrice.text = "Total Price: Rp 0"
        }
    }
    
    private fun handleBuyFilm() {
        val quantityText = binding.etQuantity.text.toString().trim()
        
        // Validate quantity
        if (quantityText.isEmpty()) {
            showToast("Quantity must be filled")
            return
        }
        
        if (!ValidationUtils.isQuantityValid(quantityText)) {
            showToast("Please enter a valid quantity")
            return
        }
        
        val quantity = quantityText.toInt()
        val totalPrice = quantity * (film?.price ?: 0.0)
        
        // Create transaction
        val transaction = Transaction(
            userId = sessionManager.getUserId(),
            filmId = filmId,
            quantity = quantity,
            totalPrice = totalPrice
        )
        
        // Insert transaction to database
        val transactionId = databaseHelper.insertTransaction(transaction)
        
        if (transactionId > 0) {
            showToast("Transaction successful!")
            finish()
        } else {
            showToast("Transaction failed. Please try again.")
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}