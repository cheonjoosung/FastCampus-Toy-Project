package com.example.fastcampus.ch15_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fastcampus.ApiKey.WEATHER_API_KEY
import com.example.fastcampus.ch15_weather.network.WeatherService
import com.example.fastcampus.databinding.ActivityWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    private val url = "http://apis.data.go.kr/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //위도 37.48814860666663, 경도 126.90514411770405
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)

        val baseDateTime = BaseDateTime.getBaseDateTime()
        val converter = GeoPointConverter()
        val point = converter.convert(lat = 37.48814860666663, lon = 126.90514411770405)

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