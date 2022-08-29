package com.mahmutbulut.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    val day: Int,
    val temperature: String,
    val wind: String
):Parcelable