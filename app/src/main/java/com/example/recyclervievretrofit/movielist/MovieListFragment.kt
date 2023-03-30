package com.example.recyclervievretrofit.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recyclervievretrofit.R
import com.example.recyclervievretrofit.constants.*
import com.example.recyclervievretrofit.databinding.FragmentMovieListBinding
import com.example.recyclervievretrofit.models.Movie


class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    //Инстенс viewModel + фабрика
    private val viewModel: MovieListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MovieListRepository()
                return MovieListViewModel(repository) as T
            }
        }
    }

    //Инстанс Adapter для RecyclerView
    private val movieAdapter = MovieAdapter()

    //Инстанс AdapterList() для RecyclerView
    private val movieAdapterList = MovieListAdapter { movie -> onItemClick(movie) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Передаем ViewModel в XML через dataBinding
        binding.viewModelXML = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //Передаем Адаптер в RecyclerView
        binding.recyclerView.adapter = movieAdapterList


        //Настраиваем 2 Spinner-а, для того, чтобы у пользователя была возможность выбрать
        //год и месяц для загрузки:
        val spinnerYear = binding.SpinnerYer
        //Создаем адаптер для Spinner
        spinnerYear.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, YEAR_ARRAY)
        //Устанавливаем Лисенер для Спинера:

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            spinnerYear.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    //Действие когда что-то выбрано:
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) { //Эмитим в поток viewModel выбранный пользователем год:
                        if (OLD_POSITION_YEAR != position) {
                            OLD_POSITION_YEAR = position
                            viewModel.moviesYearFlow.value = YEAR_ARRAY[position]
                            viewModel.refresh()
                        }
                    }

                    //Действие если ничего не выбрано:
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

        }


        val spinnerMonth = binding.SpinnerMonth
        spinnerMonth.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, MONTH_ARRAY_TO_USER)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                //Действие когда что-то выбрано:
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) { //Эмитим в поток viewModel выбранный пользователем месяц:
                    if (OLD_POSITION_MONTH != position) {
                        OLD_POSITION_MONTH = position
                        viewModel.moviesMonthFlow.value = MONTH_ARRAY[position]
                        viewModel.refresh()
                    }
                }

                //Действие если ничего не выбрано:
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }


        //Загружаем данные в адаптер (из потока viewModel)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.moviesFlow.collect {
                //movieAdapter.setData(it)
                movieAdapterList.submitList(it)
                binding.textViewValue.text = it.size.toString()
            }
        }

        //Лисенер swipeRefreshLayout (Действие при свапе/решреше)
        binding.swipeRefreshLayout.setOnRefreshListener {
            //Отключим показ встроенного в swipeRefreshLayout ProgressBara-а
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            putString(KEY_NAME_RU, item.nameRu)
            putString(KEY_GENRES, item.genres.joinToString(",") { it.genre })
            putString(KEY_PREMIERE_RU, "Премьера: ${item.premiereRu}")
            putString(KEY_COUNTRIES, item.countries.joinToString(",") { it.country })
            putString(KEY_POSTER_URL, item.posterUrl)
        }
        findNavController().navigate(
            R.id.action_movieListFragment_to_onClickItemFragment,
            bundle
        )
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}