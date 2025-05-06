package com.dojomovie.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojomovie.app.databinding.ItemTransactionBinding
import com.dojomovie.app.models.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter(
    private var transactions: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }
    
    override fun getItemCount(): Int = transactions.size
    
    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
    
    inner class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(transaction: Transaction) {
            binding.apply {
                tvFilmTitle.text = transaction.filmTitle ?: "Unknown Film"
                tvFilmPrice.text = "Rp ${String.format("%,.0f", transaction.filmPrice ?: 0.0)}"
                tvQuantity.text = "Quantity: ${transaction.quantity}"
                tvTotalPrice.text = "Total: Rp ${String.format("%,.0f", transaction.totalPrice)}"
                tvTransactionDate.text = dateFormat.format(transaction.transactionDate)
            }
        }
    }
}