package com.example.recyclervievretrofit.mainui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recyclervievretrofit.R
import com.example.recyclervievretrofit.constants.*
import com.example.recyclervievretrofit.databinding.FragmentMoviePagedListBinding
import com.example.recyclervievretrofit.databinding.FragmentOnClickItemBinding
import com.example.recyclervievretrofit.pagedmovielist.*


class OnClickItemFragment : Fragment() {

    private var _binding: FragmentOnClickItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentOnClickItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewTitle.text = arguments?.getString(KEY_NAME_RU) ?:""
            textViewGenres.text = arguments?.getString(KEY_GENRES) ?:""
            textViewDescription.text = arguments?.getString(KEY_PREMIERE_RU) ?:""
            textViewCountries.text = arguments?.getString(KEY_COUNTRIES) ?:""
            //Загружаем постер в imageView через библиотеку Coil:
            arguments?.getString(KEY_POSTER_URL).let {
                imageViewPoster.load(it)
            }
        }
        //Для возврата в пред идущий фрагмент (который находится выше в стеке)
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}