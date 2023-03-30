package com.example.recyclervievretrofit.movielist

import com.example.recyclervievretrofit.api.AvailableMonths
import com.example.recyclervievretrofit.api.retrofit
import com.example.recyclervievretrofit.models.Movie
import kotlinx.coroutines.delay

class MovieListRepository {
    suspend fun getPremieres(year:Int,month: String): List<Movie> {
        //delay(3000)
        return retrofit.movies(year,month).items
    }
}