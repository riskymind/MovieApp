package com.thisiskelechi.movieapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.thisiskelechi.movieapp.data.api.TheMovieDBInterface
import com.thisiskelechi.movieapp.data.respository.MovieDetailsNetworkSource
import com.thisiskelechi.movieapp.data.respository.NetworkState
import com.thisiskelechi.movieapp.data.value_object.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}