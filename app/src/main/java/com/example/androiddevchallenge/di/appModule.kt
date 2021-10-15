package com.example.androiddevchallenge.di

import androidx.lifecycle.SavedStateHandle
import com.example.androiddevchallenge.data.FakePuppyRepository
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.ui.detail.DetailViewModel
import com.example.androiddevchallenge.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { FakePuppyRepository() }

    viewModel { HomeViewModel(get()) }

    viewModel { (savedStateHandle: SavedStateHandle) -> DetailViewModel(savedStateHandle, get()) }
}