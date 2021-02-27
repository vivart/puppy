package com.example.androiddevchallenge

import com.example.androiddevchallenge.data.FakePuppyRepository
import com.example.androiddevchallenge.data.Repository

interface AppContainer {
    val repository: Repository
}

class AppContainerImpl : AppContainer {
    override val repository = FakePuppyRepository()
}