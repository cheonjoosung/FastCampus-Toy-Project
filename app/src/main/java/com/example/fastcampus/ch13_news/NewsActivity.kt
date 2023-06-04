package com.example.fastcampus.ch13_news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

        newsAdapter = NewsAdapter { url ->
            startActivity(
                Intent(this, WebViewActivity::class.java).apply {
                    putExtra("url", url)
                }
            )
        }

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

        val newsService = retrofit.create(NewsService::class.java)

        binding.feedChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.feedChip.isChecked = true
            newsService.mainFeed().submitList()
        }

        binding.politicsChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.politicsChip.isChecked = true
            newsService.politicsNews().submitList()
        }

        binding.economyChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.economyChip.isChecked = true
            newsService.economyNews().submitList()
        }

        binding.socialChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.socialChip.isChecked = true
            newsService.societyNews().submitList()
        }

        binding.itChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.itChip.isChecked = true
            newsService.itNews().submitList()
        }

        binding.sportsChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.sportsChip.isChecked = true
            newsService.sportsNews().submitList()
        }

        binding.searchTextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.chipGroup.clearCheck()

                // 키보드 숨기기
                binding.searchTextInputEditText.clearFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                newsService.search(binding.searchTextInputEditText.text.toString()).submitList()

                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        newsService.mainFeed().submitList()
    }

    private fun Call<NewsRss>.submitList() {
        enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e(localClassName, "onResponse ${response.body()?.channel?.items}")

                val list = response.body()?.channel?.items.orEmpty().transform()
                newsAdapter.submitList(list)

                binding.notFoundView.isVisible = list.isEmpty()

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