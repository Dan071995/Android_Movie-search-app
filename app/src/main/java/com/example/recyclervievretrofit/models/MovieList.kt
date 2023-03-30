package com.example.recyclervievretrofit.models

import com.google.gson.annotations.SerializedName

class MovieList(
    @SerializedName("total") val total:Int,
    @SerializedName("items") val items:List<Movie>
)