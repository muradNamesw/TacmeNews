package com.tacme.news.components.interfaces

import android.os.Bundle

interface ViewPagerItem {
    fun onPageSelected()
    fun onPageUnselected()
    fun onMessage(messageId: Int, data: Bundle?)
    var pageIndex: Int
}