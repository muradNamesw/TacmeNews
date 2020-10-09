package com.tacme.news.view.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tacme.news.R
import com.tacme.news.databinding.ActivityMainMvvmBinding

import com.tacme.news.view.activities.base.RootActivity
import com.tacme.news.viewmodels.mvvm.MainNewsMVVMViewModel

class MainNewsMVVMActivity: RootActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainMvvmBinding
    private lateinit var viewModel: MainNewsMVVMViewModel



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main_mvvm)


        bindViewModel()


    }

    private fun bindViewModel() {
//        requestPermissions()

    }

    override fun onClick(v: View?) {

    }



}