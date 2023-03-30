package com.example.recyclervievretrofit.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.recyclervievretrofit.databinding.MovieItemBinding
import com.example.recyclervievretrofit.models.Movie

class MovieListAdapter(
    val onClick:(Movie) -> Unit
):ListAdapter<Movie,MovieViewHolder>(DiffUtilCallBack()) {

    //В данной функции необходимо просто создать разметку для элемента хранителя xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewHolder(binding)
    }

    //В этой функции устанавливаем данные в элемент
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)//Достаем конкретный фильм из массива
        with(holder.binding){
            textViewTitle.text = item?.nameRu ?:""
            textViewGenres.text = item?.genres?.joinToString(",") { it.genre }
            textViewDescription.text = "Премьера ${item?.premiereRu}"
            textViewCountries.text = item?.countries?.joinToString(",") { it.country }
            //Загружаем постер в imageView через библиотеку Coil:
            item?.let {
                imageViewPoster.load(it.posterUrl)
            }
        }
        //Установим клик лисенер для элементов
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

//Создаем требуемый для ListAdapter Callback. В нем мы описываем логику по которой будут сравниваться элементы между собой
class DiffUtilCallBack:DiffUtil.ItemCallback<Movie>(){
    //Вызывается чтобы проверить, что 2 разных объекта описывают один и тот же элемент из набора данных
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId
    //Вызывается чтобы проверить, что у 2ух разных объектов одни и те-же данные
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}