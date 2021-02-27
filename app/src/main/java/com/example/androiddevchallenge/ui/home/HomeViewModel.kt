package com.example.androiddevchallenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.data.PuppyData
import com.example.androiddevchallenge.data.Repository

class HomeViewModel(repository: Repository) :
    ViewModel() {
    private val _data: MutableLiveData<List<PuppyData>> by lazy {
        MutableLiveData<List<PuppyData>>().apply {
            value = repository.getAllPuppyData()
        }
    }
    val data: LiveData<List<PuppyData>> = _data
}


class HomeViewModelFactory(
    private val repository: Repository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}