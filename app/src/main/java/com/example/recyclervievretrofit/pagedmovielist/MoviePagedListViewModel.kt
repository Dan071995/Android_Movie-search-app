package com.example.recyclervievretrofit.pagedmovielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclervievretrofit.models.Movie
import kotlinx.coroutines.flow.*

class MoviePagedListViewModel : ViewModel() {

    //Поток с Пагинировынными фильмами. Paging Library предоставляет данные особым способом – в виде
    //Flow объектов PagingData:
    val pagedMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = null,
        pagingSourceFactory = { MoviePagingSource() }
    ).flow.cachedIn(viewModelScope)

}