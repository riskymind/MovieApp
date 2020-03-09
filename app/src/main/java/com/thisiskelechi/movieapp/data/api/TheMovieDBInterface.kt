package com.thisiskelechi.movieapp.data.api

import com.thisiskelechi.movieapp.data.value_object.MovieDetails
import com.thisiskelechi.movieapp.data.value_object.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    //Movie Detail
//    https://api.themoviedb.org/3/movie/475557?api_key=3eb71a6638d0e63a6c3a16d5b8eabfad&language=en-US

    //Movie Popularity
//    https://api.themoviedb.org/3/movie/popular?api_key=3eb71a6638d0e63a6c3a16d5b8eabfad&language=en-US&page=1

    //Domain
//    https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") pagw: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int): Single<MovieDetails>
}