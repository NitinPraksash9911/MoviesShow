package com.example.moviesshow.ui.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviesshow.databinding.MovieItemViewBinding
import com.example.moviesshow.datasource.model.MovieData.Movies
import com.example.moviesshow.ui.moviesearch.adapter.MovieListAdapter.MovieViewHolder
import com.example.moviesshow.utils.loadImageWithUrl

class MovieListAdapter(private var itemCallback: (position: Int) -> Unit) :
    ListAdapter<Movies, MovieViewHolder>(BreedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = MovieItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding, itemCallback)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class MovieViewHolder(private val binding: MovieItemViewBinding, itemCallback: (position: Int) -> Unit) :
        ViewHolder(binding.root) {

        init {
            binding.parentLayout.setOnClickListener {
                itemCallback(adapterPosition)
            }
        }

        fun bind(movie: Movies) {
            binding.movieIv.loadImageWithUrl(movie.posterUrl)
            binding.movieNameTv.text = movie.title
        }

    }

}

class BreedDiffCallback : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }
}