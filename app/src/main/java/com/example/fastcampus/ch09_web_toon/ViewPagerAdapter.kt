package com.example.fastcampus.ch09_web_toon

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val webToonActivity: WebToonActivity,
) : FragmentStateAdapter(webToonActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WebViewFragment(position, "https://comic.naver.com/webtoon?tab=mon").apply {
                listener = webToonActivity
            }
            1 -> WebViewFragment(position, "https://comic.naver.com/webtoon/list?titleId=650305").apply {
                listener = webToonActivity
            }
            else -> WebViewFragment(position, "https://comic.naver.com/webtoon/list?titleId=808198").apply {
                listener = webToonActivity
            }
        }
    }
}