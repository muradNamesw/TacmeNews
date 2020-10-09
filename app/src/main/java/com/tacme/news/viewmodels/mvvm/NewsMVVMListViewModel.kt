package com.tacme.news.viewmodels.mvvm

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apprikot.listable.interfaces.Listable
import com.tacme.news.networking.WebServiceError
import com.tacme.news.networking.WebServiceError.malformedOutput
import com.tacme.news.networking.WebServices
import com.tacme.news.viewmodels.BaseViewModel
import java.util.*

class NewsMVVMListViewModel : BaseViewModel {




    var currentIndex:Int    = 1
    var count:Int           = 20
    var totalItems:Int      = 0

    var showNoResults:       ( (Boolean)         -> Unit )? = null
    var updateProgressState: ( (Boolean)         -> Unit )? = null
    var onDataLoaded:        ( ()                -> Unit )? = null
    var onError:             ( (WebServiceError) -> Unit )? = null


    var itemsList = MutableLiveData<ArrayList<Listable>>()

    constructor(application: Application) : super(application) {
        itemsList.value = arrayListOf<Listable>()
    }

    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return NewsMVVMListViewModel(
                application
            ) as T
        }
    }


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

        this.updateProgressState?.invoke(true)

        WebServices.newsListMVVM("us", "business", "d7e202c9ef894336a43820c105df8284", { status, newsListDec, totalItemsCount ->

            try {

                if (status > 0) {

                    totalItems = totalItemsCount

                    itemsList.value?.addAll(listOf(*newsListDec))

                    this.showNoResults?.invoke(itemsList.value?.size == 0)

                    this.onDataLoaded?.invoke()

                } else {
                    this.onError?.invoke(malformedOutput)
                    this.showNoResults?.invoke(true)
                }

            } catch (e: Exception) {
                this.onError?.invoke(malformedOutput)
                this.showNoResults?.invoke(true)
            } finally {
                this.updateProgressState?.invoke(false)
            }

        }, { error ->

            this.updateProgressState?.invoke(false)
            this.showNoResults?.invoke(true)
            this.onError?.invoke(error)
        })

    }

}
