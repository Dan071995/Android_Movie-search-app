package com.example.recyclervievretrofit.pagedmovielist

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.recyclervievretrofit.R
import com.example.recyclervievretrofit.databinding.MovieItemBinding
import com.example.recyclervievretrofit.models.Movie
import com.example.recyclervievretrofit.movielist.MovieViewHolder

class MoviePagingDataAdapter(
    private val onClick: (Movie) -> Unit
):PagingDataAdapter<Movie, MovieViewHolder>(DiffUtilCallBack()) {

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
            //Усианавливаем ClickListener на каждый элемент списка:
            root.setOnClickListener {
                item?.let {
                    onClick(it)
                }
            }

            textViewInfo.setOnClickListener { view ->
                showTooltip(view, position)
            }
        }
    }
}

private fun showTooltip(anchorView: View, position: Int) {
    val context = anchorView.context
    val inflater = LayoutInflater.from(context)
    val tooltipView = inflater.inflate(R.layout.tooltip_layout, null)

    // Настройка текста подсказки
    val tooltipText = tooltipView.findViewById<TextView>(R.id.tooltip_text)
    tooltipText.text = "[PA] Hint for item at position $position"

    // Создание PopupWindow
    val popupWindow = PopupWindow(
        tooltipView,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        true
    )

    // Показ PopupWindow
    popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.START)

    // Закрытие PopupWindow через 3 секунды
    Handler(Looper.getMainLooper()).postDelayed({
        popupWindow.dismiss()
    }, 3000)
}

//Создаем требуемый для DataAdapter Callback. В нем мы описываем логику по которой будут сравниваться элементы между собой
class DiffUtilCallBack:DiffUtil.ItemCallback<Movie>(){
    //Вызывается чтобы проверить, что 2 разных объекта описывают один и тот же элемент из набора данных
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId
    //Вызывается чтобы проверить, что у 2ух разных объектов одни и те-же данные
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}