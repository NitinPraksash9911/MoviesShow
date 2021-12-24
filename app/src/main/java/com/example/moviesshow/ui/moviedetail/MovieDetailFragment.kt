package com.example.moviesshow.ui.moviedetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.moviesshow.R.string
import com.example.moviesshow.arch.BaseFragment
import com.example.moviesshow.arch.ViewState.Empty
import com.example.moviesshow.arch.ViewState.Error
import com.example.moviesshow.arch.ViewState.Loading
import com.example.moviesshow.arch.ViewState.Success
import com.example.moviesshow.databinding.FragmentMovieDetailBinding
import com.example.moviesshow.ui.moviesearch.TAG
import com.example.moviesshow.ui.viewmodel.MovieSearchViewModel
import com.example.moviesshow.utils.collectOnLifeCycleScope
import com.example.moviesshow.utils.hide
import com.example.moviesshow.utils.loadImageWithUrl
import com.example.moviesshow.utils.show
import com.example.moviesshow.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    val argument: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieSearchViewModel by activityViewModels()

    override fun initViews(savedInstanceState: Bundle?) {

        val movieId = argument.moiveId

        setMovieDetail(movieId)

    }

    private fun setMovieDetail(movieId: String) {

        collectOnLifeCycleScope(viewModel.fetchMovieDetail(movieId)) { moviesEvent ->

            when (moviesEvent) {
                is Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.errorTv.hide()
                    Timber.d(TAG, "Loading")
                }
                is Success -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.errorTv.hide()
                    binding.movieIv.loadImageWithUrl(moviesEvent.item.poster)
                    binding.movieNameTv.text = moviesEvent.item.title
                    binding.plotTv.text = moviesEvent.item.plot
                }
                is Empty -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.errorTv.show()
                    binding.errorTv.text = getString(string.no_found)
                }
                is Error -> {
                    binding.progressCircular.visibility = View.GONE
                    moviesEvent.errorMsg.snack(Color.RED, binding.root)
                    binding.errorTv.show()
                    binding.errorTv.text = moviesEvent.errorMsg
                    Timber.d(TAG, moviesEvent.errorMsg)
                }
            }
        }

    }

}