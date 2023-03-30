package com.example.recyclervievretrofit.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.recyclervievretrofit.databinding.MovieItemBinding
import com.example.recyclervievretrofit.models.Movie

class MovieAdapter:RecyclerView.Adapter<MovieViewHolder>(){

    private var data:List<Movie> = emptyList()

    //Перересовываем RecyclerView когда получаем от сервера новую порцию фильмов
    fun setData(data:List<Movie>){
        this.data = data
        notifyDataSetChanged()
    }

    //Считаем кол-во элементов, которые нужно будет отрисовать
    override fun getItemCount(): Int {
        return data.size
    }

    //В данной функции необходимо просто создать разметку для элемента хранителя xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewHolder(binding)

    }

    //В этой функции устанавливаем данные в элемент
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = data.getOrNull(position) //Достаем конкретный фильм из массива
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
    }

}

class MovieViewHolder(val binding: MovieItemBinding):ViewHolder(binding.root)
