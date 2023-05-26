package com.example.fastcampus.ch09_web_toon

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.WEB_HISTORY
import com.example.fastcampus.databinding.ActivityWebToonBinding
import com.google.android.material.tabs.TabLayoutMediator

class WebToonActivity : AppCompatActivity(), OnTabLayoutNameChanged {

    private lateinit var binding: ActivityWebToonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebToonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences(WEB_HISTORY, Context.MODE_PRIVATE)
        val tab0 = sharedPreference?.getString("tab0_name", "position 0")
        val tab1 = sharedPreference?.getString("tab1_name", "position 1")
        val tab2 = sharedPreference?.getString("tab2_name", "position 2")

        with(binding) {

            viewPager.adapter = ViewPagerAdapter(this@WebToonActivity)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                run {
                    tab.text = when (position) {
                        0 -> tab0
                        1 -> tab1
                        else -> tab2
                    }
                }
            }.attach()

        }
    }

    override fun onBackPressed() {

        supportFragmentManager.fragments[binding.viewPager.currentItem].let {
            when (it) {
                is WebViewFragment -> {
                    if (it.canGoBack()) {
                        it.goBack()
                    } else {
                        super.onBackPressed()
                    }
                }

                else -> {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}