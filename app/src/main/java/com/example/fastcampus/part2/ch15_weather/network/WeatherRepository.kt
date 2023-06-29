package com.example.fastcampus.part2.ch15_weather.network

import com.example.fastcampus.ApiKey
import com.example.fastcampus.part2.ch15_weather.BaseDateTime
import com.example.fastcampus.part2.ch15_weather.Category
import com.example.fastcampus.part2.ch15_weather.Forecast
import com.example.fastcampus.part2.ch15_weather.GeoPointConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {

    private const val url = "http://apis.data.go.kr/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    fun callWeather(
        latitude: Double,
        longitude: Double,
        successCallback: (List<Forecast>) -> Unit,
        failureCallback: (Throwable) -> Unit
    ) {
        //위도 37.48814860666663, 경도 126.90514411770405
        val baseDateTime = BaseDateTime.getBaseDateTime()
        val converter = GeoPointConverter()
        val point = converter.convert(lat = latitude, lon = longitude)

        service.getVillageForecast(
            serviceKey = ApiKey.WEATHER_API_KEY,
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

                if (forecastList.isEmpty()) {
                    failureCallback(NullPointerException())
                } else {
                    successCallback(forecastList)
                }
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                failureCallback(t)
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