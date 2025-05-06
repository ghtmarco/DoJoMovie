package com.dojomovie.app.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojomovie.app.adapters.TransactionAdapter
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.ActivityHistoryBinding
import com.dojomovie.app.utils.SessionManager

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private lateinit var transactionAdapter: TransactionAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)
        
        setupUI()
        loadTransactionHistory()
    }
    
    private fun setupUI() {
        // Setup RecyclerView
        transactionAdapter = TransactionAdapter(emptyList())
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = transactionAdapter
        }
        
        // Setup back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadTransactionHistory() {
        val userId = sessionManager.getUserId()
        val transactions = databaseHelper.getTransactionsByUserId(userId)
        
        if (transactions.isEmpty()) {
            binding.tvEmptyMessage.visibility = View.VISIBLE
            binding.recyclerViewHistory.visibility = View.GONE
        } else {
            binding.tvEmptyMessage.visibility = View.GONE
            binding.recyclerViewHistory.visibility = View.VISIBLE
            transactionAdapter.updateTransactions(transactions)
        }
    }
}