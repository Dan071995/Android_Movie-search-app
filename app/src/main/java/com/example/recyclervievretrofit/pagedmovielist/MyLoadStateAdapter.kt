package com.example.recyclervievretrofit.pagedmovielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclervievretrofit.databinding.LoadStateBinding

class MyLoadStateAdapter: LoadStateAdapter<LoadStateViewHolder>(){

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        //Не нужно реализовывать, поскольку мы не будем устанавливать какие-либо данные
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }
}

class LoadStateViewHolder(binding:LoadStateBinding):ViewHolder(binding.root)