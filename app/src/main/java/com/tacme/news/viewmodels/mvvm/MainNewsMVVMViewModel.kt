package com.tacme.news.viewmodels.mvvm

//import android.arch.lifecycle.MutableLiveData
//import android.arch.lifecycle.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tacme.news.viewmodels.BaseViewModel


class MainNewsMVVMViewModel : BaseViewModel {



    constructor(application: Application) : super(application)

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return MainNewsMVVMViewModel(
                application
            ) as T
        }
    }


}
