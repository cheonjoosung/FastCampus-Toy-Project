package com.example.fastcampus.part2.ch09_web_toon

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val webToonActivity: com.example.fastcampus.part2.ch09_web_toon.WebToonActivity,
) : FragmentStateAdapter(webToonActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> com.example.fastcampus.part2.ch09_web_toon.WebViewFragment(
                position,
                "https://comic.naver.com/webtoon?tab=mon"
            ).apply {
                listener = webToonActivity
            }
            1 -> com.example.fastcampus.part2.ch09_web_toon.WebViewFragment(
                position,
                "https://comic.naver.com/webtoon/list?titleId=650305"
            ).apply {
                listener = webToonActivity
            }
            else -> com.example.fastcampus.part2.ch09_web_toon.WebViewFragment(
                position,
                "https://comic.naver.com/webtoon/list?titleId=808198"
            ).apply {
                listener = webToonActivity
            }
        }
    }
}