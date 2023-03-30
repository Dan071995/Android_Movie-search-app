package com.example.recyclervievretrofit.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclervievretrofit.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: MovieListRepository
):ViewModel(){
    //Поток с пользовательским выбором года
    val moviesYearFlow = MutableStateFlow<Int>(1995)
    //Поток с пользовательским выбором месяца
    val moviesMonthFlow = MutableStateFlow<String>("JANUARY")


    //Поток с состояниями ProgressBar
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    //Поток с состояниями CheckBox
    val filterEnabled= MutableStateFlow<Boolean>(false)

    //Поток с списком фильмов
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    //Филтрация списка с помощью опреатора combine
    val moviesFlow :StateFlow<List<Movie>> = combine(_movies,filterEnabled){ movies, filterEnabled ->
        if(filterEnabled){
            movies.filter { movie->
                movie.countries.any { it.country == "Россия" }
            }
        }
        else movies
        //Далее с помощью stateIn преобразуем Flow (результат combine()) в StateFlow()
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = _movies.value)

    init {
        loadPremiers()
    }

    private fun loadPremiers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _isLoading.value = true //Загрузка началась
                repository.getPremieres(moviesYearFlow.value, moviesMonthFlow.value)
            }.fold(
                onSuccess = { _movies.value = it },
                onFailure = { Log.d("MovieListViewModel",it.message ?:"")}
            )
            _isLoading.value = false //Загрузка закончилась
        }
    }
    //Загружаем новый список вильмов при рефреше (SwipeRefreshLayout)
    fun refresh(){
        loadPremiers()
    }

}