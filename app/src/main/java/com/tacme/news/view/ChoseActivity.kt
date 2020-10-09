package com.tacme.news.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tacme.news.R
import com.tacme.news.databinding.ActivityChoseBinding
import com.tacme.news.view.activities.base.RootActivity
import com.tacme.news.view.mvp.NewsMVPActivity
import com.tacme.news.view.mvvm.MainNewsMVVMActivity
import com.tacme.news.viewmodels.chose.ChoseViewModel


/**
 * Created by Ahmad on 13/11/19.
 */
class ChoseActivity : RootActivity() {

    private lateinit var viewModel: ChoseViewModel
    lateinit var binding: ActivityChoseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chose)

        bindViewModel()

        updateViews()

    }

    private fun bindViewModel() {


        viewModel = ViewModelProvider(this).get(ChoseViewModel::class.java)


    }

    private fun updateViews() {
        binding.mvpButton.setOnClickListener {
            val intent = Intent(this, NewsMVPActivity::class.java)
            startActivity(intent)
//            finish()
        }
        binding.mvvmButton.setOnClickListener {
            val intent = Intent(this, MainNewsMVVMActivity::class.java)
            startActivity(intent)
//            finish()
        }


    }

}
