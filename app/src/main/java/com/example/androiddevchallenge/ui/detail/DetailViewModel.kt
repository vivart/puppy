package com.example.androiddevchallenge.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.data.PuppyData
import com.example.androiddevchallenge.data.Repository

class DetailViewModel(id: String, repository: Repository) : ViewModel() {
    private val _data: MutableLiveData<PuppyData> by lazy {
        MutableLiveData<PuppyData>().apply {
            value = repository.getPuppyData(id)
        }
    }
    val data: LiveData<PuppyData> = _data
}

class DetailViewModelFactory(
    private val id: String,
    private val repository: Repository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(id, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}