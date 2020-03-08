package com.thisiskelechi.movieapp.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.thisiskelechi.movieapp.R
import com.thisiskelechi.movieapp.data.api.POSTER_BASE_URL
import com.thisiskelechi.movieapp.data.api.TheMovieDBClient
import com.thisiskelechi.movieapp.data.api.TheMovieDBInterface
import com.thisiskelechi.movieapp.data.respository.NetworkState
import com.thisiskelechi.movieapp.data.value_object.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*


class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRespository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)
        val apiService: TheMovieDBInterface =  TheMovieDBClient.getClient()
        movieRespository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUi(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + "minutes"
        movie_overview.text = it.overview

        val formatCurrent = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrent.format(it.budget)
        movie_revenue.text = formatCurrent.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(movie_poster)
    }

    private fun getViewModel(movieId: Int) : SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRespository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
