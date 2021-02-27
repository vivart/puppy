package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes
import com.example.androiddevchallenge.R

data class PuppyData(
    val id: String,
    val breed: String,
    val description: String,
    @DrawableRes val imageRes: Int = R.drawable.german_shepherd
)