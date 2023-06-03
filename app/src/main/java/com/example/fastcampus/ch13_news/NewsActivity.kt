package com.example.fastcampus.ch13_news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.ch13_news.network.NewsRss
import com.example.fastcampus.ch13_news.network.NewsService
import com.example.fastcampus.databinding.ActivityNewsBinding
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private lateinit var newsAdapter: NewsAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://news.google.com/")
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .build()
            )
        )
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsAdapter = NewsAdapter()

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

        val newsService = retrofit.create(NewsService::class.java)
        newsService.mainFeed().enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e(localClassName, "onResponse ${response.body()?.channel?.items}")

                val list = response.body()?.channel?.items.orEmpty().transform()
                newsAdapter.submitList(list)

                list.forEachIndexed { index, newsModel ->
                    Thread {
                        try {
                            val jsoup = Jsoup.connect(newsModel.link)
                                .get()

                            val elements = jsoup.select("meta[property=og:image]")
                            val ogImageNode = elements.find { node ->
                                node.attr("property") == "og:image"
                            }

                            newsModel.imageUrl = ogImageNode?.attr("content")
                        } catch (e: Exception) {
                            Log.e(localClassName, "Error $e")
                        }

                        runOnUiThread {
                            newsAdapter.notifyItemChanged(index)
                        }
                    }.start()
                }

            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                Log.e(localClassName, "Failed ${t.message}")
            }

        })
    }
}