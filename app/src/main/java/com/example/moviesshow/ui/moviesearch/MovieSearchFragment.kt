package com.example.moviesshow.ui.moviesearch

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.moviesshow.R
import com.example.moviesshow.R.string
import com.example.moviesshow.arch.BaseFragment
import com.example.moviesshow.arch.ViewState
import com.example.moviesshow.arch.ViewState.Empty
import com.example.moviesshow.arch.ViewState.Error
import com.example.moviesshow.arch.ViewState.Loading
import com.example.moviesshow.arch.ViewState.Success
import com.example.moviesshow.databinding.FragmentMovieSearchBinding
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.ui.moviesearch.adapter.MovieListAdapter
import com.example.moviesshow.ui.viewmodel.MovieSearchViewModel
import com.example.moviesshow.utils.collectOnLifeCycleScope
import com.example.moviesshow.utils.getQueryTextChangeStateFlow
import com.example.moviesshow.utils.hide
import com.example.moviesshow.utils.show
import com.example.moviesshow.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber

const val TAG = "MovieSearchFragment"

@InternalCoroutinesApi
@AndroidEntryPoint
class MovieSearchFragment : BaseFragment<FragmentMovieSearchBinding>(FragmentMovieSearchBinding::inflate) {

    private val viewModel: MovieSearchViewModel by activityViewModels()

    private lateinit var movieListAdapter: MovieListAdapter

    override fun initViews(savedInstanceState: Bundle?) {

        movieListAdapter = MovieListAdapter(::onListItemClick)

        binding.movieListRv.adapter = movieListAdapter



//        binding.pullToRefresh.setOnRefreshListener {
//            viewModel.searchMovies("Marvel", 1)
//            binding.pullToRefresh.isRefreshing = false
//        }

        searchMovies()
    }

    private fun searchMovies() {

        collectOnLifeCycleScope(
            binding.movieSearchView.getQueryTextChangeStateFlow()
                .debounce(600)
                .filter {
                    (it.isNotEmpty())
                }.flatMapLatest {
                    viewModel.searchMovies(query = it)
                }
        ) { moviesEvent ->

            dataObserver(moviesEvent)

        }
    }

    private fun dataObserver(moviesEvent: ViewState<MovieData>) {
        when (moviesEvent) {
            is Loading -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.movieListRv.hide()
                binding.errorTv.hide()
                Timber.d(TAG, "Loading")
            }
            is Success -> {
                binding.progressCircular.visibility = View.GONE
                binding.errorTv.hide()
                binding.movieListRv.show()
                movieListAdapter.submitList(moviesEvent.item.movies)
            }
            is Empty -> {
                binding.progressCircular.visibility = View.GONE
                binding.errorTv.show()
                binding.errorTv.text = getString(string.no_found)
            }
            is Error -> {
                binding.progressCircular.visibility = View.GONE
                binding.movieListRv.hide()
                moviesEvent.errorMsg.snack(Color.RED, binding.root)
                binding.errorTv.show()
                binding.errorTv.text = moviesEvent.errorMsg
                Timber.d(TAG, moviesEvent.errorMsg)
            }
        }
    }

    private fun onListItemClick(position: Int) {
        val movie = movieListAdapter.currentList[position]
        val directions = MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailFragment(moiveId = movie.imdbID)
        findNavController().navigate(directions)
    }

}