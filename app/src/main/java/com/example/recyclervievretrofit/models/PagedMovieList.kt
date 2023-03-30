package com.example.recyclervievretrofit.models

import com.google.gson.annotations.SerializedName

class PagedMovieList(
    @SerializedName("pagesCount") val pagesCount:Int,
    @SerializedName("films") val films:List<Movie>
)