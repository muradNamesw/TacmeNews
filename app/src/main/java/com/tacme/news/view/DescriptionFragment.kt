package com.tacme.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tacme.news.viewmodels.mvvm.DescriptionViewModel
import com.tacme.news.R
import com.tacme.news.databinding.DescriptionFragmentBinding
import com.tacme.news.view.base.RootFragment

class DescriptionFragment : Fragment() {



    private lateinit var viewModel: DescriptionViewModel
    private lateinit var binding: DescriptionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.description_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()

        findViews()


    }


    private fun bindViewModel() {

        activity?.let {


            viewModel = ViewModelProvider(this).get(DescriptionViewModel::class.java)


            viewModel?.description = arguments?.getString(RootFragment.PARAM_REF_NO_KEY, "")



        }


    }

    private fun findViews() {

        binding.descrptionTextView.text = viewModel.description

    }


}