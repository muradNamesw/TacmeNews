package com.tacme.news.presenter.mvp

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.apprikot.listable.interfaces.Listable
import com.tacme.news.networking.WebServices
import com.tacme.news.view.base.RootFragment
import com.tacme.news.view.mvp.fragment.NewsMVPListFragment
import java.util.*

class NewsMVPListPresenter {


    constructor(newsMVPListFragment: RootFragment){
        this.newsMVPListFragment = newsMVPListFragment as NewsMVPListFragment
        itemsList.value = arrayListOf<Listable>()
    }

    private var newsMVPListFragment: NewsMVPListFragment
    var currentIndex: Int = 1
    var count: Int = 20
    var totalItems: Int = 0

    //    var showNoResults:       ( (Boolean)         -> Unit )? = null
//    var updateProgressState: ( (Boolean)         -> Unit )? = null
//    var onDataLoaded:        ( ()                -> Unit )? = null
//    var onError:             ( (WebServiceError) -> Unit )? = null
//
//
    var itemsList = MutableLiveData<ArrayList<Listable>>()


    fun loadInitial(activity: Activity?) {

        this.currentIndex = 1

        this.itemsList.value?.clear()

        this.loadNewsList(activity)

    }

    fun loadNext(activity: Activity?) {

        this.currentIndex += 1

        if (this.totalItems > this.currentIndex * count) {
            this.loadNewsList(activity)
        }
    }

    private fun loadNewsList(activity: Activity?) {


        WebServices.newsListMVP(
            "us",
            "business",
            "d7e202c9ef894336a43820c105df8284",
            { status, newsListDec, totalItemsCount ->

                try {

                    if (status > 0) {

                        totalItems = totalItemsCount

                        itemsList.value?.addAll(listOf(*newsListDec))
                        newsMVPListFragment.success(itemsList)

//
//                    this.showNoResults?.invoke(itemsList.value?.size == 0)
//
//                    this.onDataLoaded?.invoke()

                    } else {
//                    this.onError?.invoke(malformedOutput)
//                    this.showNoResults?.invoke(true)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
//                this.onError?.invoke(malformedOutput)
//                this.showNoResults?.invoke(true)
                } finally {
//                this.updateProgressState?.invoke(false)
                }

            },
            { error ->

//            this.updateProgressState?.invoke(false)
//            this.showNoResults?.invoke(true)
//            this.onError?.invoke(error)
            })

    }


    interface INewsList {
        fun success(itemsList: MutableLiveData<ArrayList<Listable>>)

    }

}
