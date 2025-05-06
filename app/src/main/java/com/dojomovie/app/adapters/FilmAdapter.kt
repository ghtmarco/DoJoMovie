package com.dojomovie.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dojomovie.app.databinding.ItemFilmBinding
import com.dojomovie.app.models.Film

class FilmAdapter(
    private var films: List<Film>,
    private val onFilmClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }
    
    override fun getItemCount(): Int = films.size
    
    fun updateFilms(newFilms: List<Film>) {
        films = newFilms
        notifyDataSetChanged()
    }
    
    inner class FilmViewHolder(
        private val binding: ItemFilmBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(film: Film) {
            binding.apply {
                tvTitle.text = film.title
                tvPrice.text = "Rp ${String.format("%,.0f", film.price)}"
                
                // Load image with Glide
                Glide.with(itemView.context)
                    .load(film.cover)
                    .centerCrop()
                    .into(ivCover)
                
                root.setOnClickListener {
                    onFilmClick(film)
                }
            }
        }
    }
}