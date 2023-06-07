package com.example.fastcampus.ch15_weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.fastcampus.ApiKey.WEATHER_API_KEY
import com.example.fastcampus.R
import com.example.fastcampus.ch15_weather.network.WeatherService
import com.example.fastcampus.databinding.ActivityWeatherBinding
import com.example.fastcampus.databinding.ItemForecastBinding
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    private val url = "http://apis.data.go.kr/"

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                updateLocation()
            }

            else -> {
                Toast.makeText(this, "위치권한 필요합니다", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun updateLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {

            val latitude = it?.latitude ?: 37.48814860666663
            val longitude = it?.longitude ?: 126.90514411770405

            Thread {
                 try {
                    val addressList = Geocoder(this, Locale.KOREA).getFromLocation(
                        latitude,
                        longitude,
                        1
                    )

                    runOnUiThread {
                        binding.locationTextView.text = addressList?.get(0)?.thoroughfare.orEmpty()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()

            callWeather(latitude, longitude)
        }
    }

    private fun callWeather(latitude: Double, longitude: Double) {
        //위도 37.48814860666663, 경도 126.90514411770405
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)

        val baseDateTime = BaseDateTime.getBaseDateTime()
        val converter = GeoPointConverter()
        val point = converter.convert(lat = latitude, lon = longitude)

        service.getVillageForecast(
            serviceKey = WEATHER_API_KEY,
            baseDate = baseDateTime.baseDate,
            baseTime = baseDateTime.baseTime,
            nx = point.nx,
            ny = point.ny
        ).enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                val list = response.body()?.response?.body?.items?.forecastEntities.orEmpty()

                val forecastDateTimeMap = mutableMapOf<String, Forecast>()

                for (item in list) {
                    val key = "${item.forecastDate}/${item.forecastTime}"
                    if (forecastDateTimeMap[key] == null) {
                        forecastDateTimeMap[key] = Forecast(
                            forecastDate = item.forecastDate,
                            forecastTime = item.forecastTime
                        )
                    }

                    forecastDateTimeMap[key]?.apply {
                        when (item.category) {
                            Category.POP -> precipitation = item.forecastValue.toInt()
                            Category.PTY -> precipitationType = transformRainType(item)
                            Category.SKY -> sky = transformSky(item)
                            Category.TMP -> temperature = item.forecastValue.toDouble()
                            else -> {}
                        }
                    }
                }

                val forecastList = forecastDateTimeMap.values.toMutableList()
                forecastList.sortWith { f1, f2 ->
                    val f1DataTime = "${f1.forecastDate}${f1.forecastTime}"
                    val f2DataTime = "${f2.forecastDate}${f2.forecastTime}"

                    return@sortWith f1DataTime.compareTo(f2DataTime)
                }

                val currentForecast = forecastList.first()
                binding.temperatureTextView.text =
                    getString(R.string.temperature_text, currentForecast.temperature)
                binding.skyTextView.text = currentForecast.weather
                binding.precipitationTextView.text =
                    getString(R.string.precipitation_text, currentForecast.precipitation)

                binding.childForecastLayout.apply {
                    forecastList.forEachIndexed { index, forecast ->
                        if (index == 0) return@forEachIndexed

                        val itemView = ItemForecastBinding.inflate(layoutInflater)
                        itemView.timeTextView.text = forecast.forecastTime
                        itemView.weatherTextView.text = forecast.weather
                        itemView.temperatureTextView.text =
                            getString(R.string.temperature_text, forecast.temperature)

                        addView(itemView.root)
                    }
                }

                Log.e(localClassName, forecastDateTimeMap.toString())
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun transformRainType(forecastEntity: ForecastEntity): String {
        return when (forecastEntity.forecastValue.toInt()) {
            0 -> "없음"
            1 -> "비"
            2 -> "비/눈"
            3 -> "눈"
            4 -> "소나기"
            else -> ""
        }
    }

    private fun transformSky(forecastEntity: ForecastEntity): String {
        return when (forecastEntity.forecastValue.toInt()) {
            1 -> "맑음"
            3 -> "구름많음"
            4 -> "흐림"
            else -> ""
        }
    }
}