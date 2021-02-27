package com.example.androiddevchallenge.data

interface Repository {
    fun getAllPuppyData(): List<PuppyData>
    fun getPuppyData(id: String): PuppyData
}