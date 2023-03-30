package com.example.recyclervievretrofit.pagedmovielist

import com.example.recyclervievretrofit.api.retrofit
import com.example.recyclervievretrofit.models.Movie
import kotlinx.coroutines.delay

class MoviePagedListRepository {
    suspend fun getTopList(page:Int):List<Movie>{
        delay(1000)
        return retrofit.topList(page).films
    }
}