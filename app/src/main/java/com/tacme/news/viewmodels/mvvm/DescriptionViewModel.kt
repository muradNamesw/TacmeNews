package com.tacme.news.viewmodels.mvvm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tacme.news.viewmodels.BaseViewModel

class DescriptionViewModel : BaseViewModel {


    var description: String? = null

    constructor(application: Application) : super(application)

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return DescriptionViewModel(
                application
            ) as T
        }
    }


}