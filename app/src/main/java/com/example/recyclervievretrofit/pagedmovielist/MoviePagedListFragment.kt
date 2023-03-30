package com.example.recyclervievretrofit.pagedmovielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.recyclervievretrofit.R
import com.example.recyclervievretrofit.constants.*
import com.example.recyclervievretrofit.databinding.FragmentMoviePagedListBinding
import com.example.recyclervievretrofit.models.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MoviePagedListFragment : Fragment() {

    private var _binding: FragmentMoviePagedListBinding? = null
    private val binding get() = _binding!!

    //Инстенс viewModel для MoviePagedListFragment
    private val viewModel: MoviePagedListViewModel by viewModels()

    //Инстанс pagedAdapter для RecyclerView
    private val pagedAdapter = MoviePagingDataAdapter{ movie ->  onItemClick(movie) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentMoviePagedListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Устанавливаем адаптер в ResyclerView:
        binding.rv.adapter = pagedAdapter.withLoadStateFooter(MyLoadStateAdapter())

        //ТАК НЕ РАБОТАЕТ!!!
        /*viewLifecycleOwner.lifecycleScope.launch {
           viewModel.pagedMovies.onEach {
                pagedAdapter.submitData(it)
            }
        }*/

        viewModel.pagedMovies.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        //В УРОКЕ НЕБЫЛО, НО Я ДОБАВИЛ ОТОБРАЖЕНИЕ РАЗМЕРА СПИСКА С ФИЛЬМАМИ
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                delay(500)
                binding.textViewFilmListSize.text = pagedAdapter.itemCount.toString()
                binding.textViewDownloadedPaged.text = PAGE_SIZE.toString()
            }
        }

        //Добавляем лисенер для SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            //Функция refresh() имеется у pagedAdapter и ее не нужно
            //определять в ViewModel
            pagedAdapter.refresh()
        }
        //Скрываем прогрессБар SwipeRefreshLayout-а, когда данные загружены
        pagedAdapter.loadStateFlow.onEach {
            binding.swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
    //Данная функция переходит на другой фрагмент
    private fun onItemClick(item: Movie){
        val bundle = Bundle().apply {
            putString(KEY_NAME_RU,item.nameRu)
            putString(KEY_GENRES,item.genres.joinToString(","){ it.genre })
            putString(KEY_PREMIERE_RU,"Премьера: ${item.premiereRu}")
            putString(KEY_COUNTRIES,item.countries.joinToString(","){ it.country })
            putString(KEY_POSTER_URL,item.posterUrl)
        }
        findNavController().navigate(R.id.action_moviePagedListFragment_to_onClickItemFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}