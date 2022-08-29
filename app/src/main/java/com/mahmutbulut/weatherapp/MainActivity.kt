package com.mahmutbulut.weatherapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mahmutbulut.weatherapp.databinding.ActivityMainBinding
import com.mahmutbulut.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel:WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()

        viewModel.weatherResponse.observe(this) { weather ->
            binding.apply {

                tvCity.text = edtCityName.text
                tvTemperature.text = weather.temperature

                val forecast1 = weather.forecast[0]
                val forecast2 = weather.forecast[1]
                val forecast3 = weather.forecast[2]

                tvForecast1.text = "${forecast1.temperature} | ${forecast1.wind}"
                tvForecast2.text = "${forecast2.temperature} | ${forecast2.wind}"
                tvForecast3.text = "${forecast3.temperature} | ${forecast3.wind}"


            }
            weather?.description?.let { description->
                binding.skyStateImg.visibility = View.VISIBLE
                if(description == "cloudy"){
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_partly_clouds))
                }
                else if(description == "Sunny"){
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_sun))
                }
                else if(description == "Partly cloudy") {
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_partly_clouds))
                }
                else if(description == "Rainy"){
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_rainy_day))
                    }
                else if(description == "Snow"){
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_snow))
                }
                else{
                    binding.skyStateImg.setImageDrawable(getDrawable(R.drawable.ic_rainy_day))
                }
            }
        }
    }

    private fun initButton() {
        binding.imgSearchCity.setOnClickListener {
            val cityName=binding.edtCityName.text.toString()
            searchData(cityName)
        }
    }

    private fun searchData(cityName: String) {
        viewModel.getWeather(cityName)
        hideKeyboard(this)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}