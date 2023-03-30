package com.example.recyclervievretrofit.pagedmovielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recyclervievretrofit.models.Movie

var PAGE_SIZE:Int = 0

class MoviePagingSource :PagingSource<Int,Movie>() {

    private val repository = MoviePagedListRepository()

    //Данная функция должна вернуть номер страницы, который будет использован
    //для обновления данных. Чаще всего, при обновлении данных, начинают загрузку
    //с самого начала => вернем первую страницу
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    //Основная функция, в которой будет происходить загрузка данных.
    //В ней необходимо загрузить порцию данных и вернуть их в виде LoadResult<Int, Movie>
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        //Номер страницы которую мы будем загружать. Если мы только что запустили приложение
        //Грузим 1-ую страницу, в противном случаее грузим страницу, которую дала библиотека
        val page = params.key ?: 1
        val movieList = repository.getTopList(page)
        PAGE_SIZE = page

        return kotlin.runCatching {
            movieList
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it, //Текущие значения
                    prevKey = null, //Пред идущий ключ
                    nextKey = if (it.isEmpty()) null else page + 1 //Следующий ключ
                )
            },
            onFailure = {
                LoadResult.Error(it) }
        )
    }
}