package com.tacme.news.view.mvvm.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apprikot.listable.interfaces.Listable
import com.tacme.news.R
import com.tacme.news.components.listeners.EndlessRecyclerViewScrollListener
import com.tacme.news.databinding.FragmentMvvmNewsListBinding
import com.tacme.news.model.mvvm.NewsListDec
import com.tacme.news.utils.ViewUtils
import com.tacme.news.view.adapters.BaseRecyclerAdapter
import com.tacme.news.view.base.RootFragment
import com.tacme.news.viewmodels.mvvm.NewsMVVMListViewModel

class NewsMVVMListFragment : RootFragment()  {

    private var viewModel: NewsMVVMListViewModel? = null
    private lateinit var binding: FragmentMvvmNewsListBinding

    private var dialog: Dialog? = null

    private var recyclerAdapter: BaseRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mvvm_news_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindViewModel()

        bindAdapter()

        findViews()


    }


    private fun bindViewModel() {

        activity?.let {

            if (viewModel == null) {

                viewModel = ViewModelProvider(this)
                    .get(NewsMVVMListViewModel::class.java)

                binding.isEmpty = true

                viewModel?.itemsList?.observe(
                    viewLifecycleOwner,
                    Observer<ArrayList<Listable>?> { newImportDecList ->

                        newImportDecList?.let {
                            binding.isEmpty = false
                            recyclerAdapter?.swapItems(it)
                        }

                    })

                viewModel?.showNoResults = { visible ->

                    binding.isEmpty = visible

                }



                viewModel?.updateProgressState = { visible ->

                    if (visible) {

                        dialog?.show()

                    } else {

                        dialog?.dismiss()
                        binding.swipeRefreshLayout.isRefreshing = false

                    }
                }

                viewModel?.onDataLoaded = {

                    recyclerAdapter?.swapItems(viewModel?.itemsList?.value)
                    binding.swipeRefreshLayout.isRefreshing = false

                }

                viewModel?.onError = { error ->

                    ViewUtils.handleError(activity, error)

                }

                viewModel?.loadInitial(this.activity)

            }

        }

    }

    private fun findViews() {



        val linearLayoutManager = LinearLayoutManager(this.activity)

        binding.recyclerView?.layoutManager = linearLayoutManager
        binding.recyclerView?.setHasFixedSize(true)




        // SwipeRefreshLayout
        binding.swipeRefreshLayout?.setOnRefreshListener { viewModel?.loadInitial(activity) }

        binding.swipeRefreshLayout?.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )



        binding.recyclerView?.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(linearLayoutManager) {

            public override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: androidx.recyclerview.widget.RecyclerView
            ) {

                viewModel?.loadNext(activity)

            }
        })




    }

    private fun bindAdapter() {

        activity?.applicationContext?.let {

            if (recyclerAdapter == null) {
                recyclerAdapter = BaseRecyclerAdapter(viewModel?.itemsList?.value ?: arrayListOf())

                binding.recyclerView.adapter = recyclerAdapter

                recyclerAdapter?.onClickItem = { _, viewId, anyObject ->

                    val args = Bundle()

                    if (anyObject is NewsListDec) {
                        args.putString(PARAM_REF_NO_KEY, anyObject.description)
                    }


                    when (viewId?.id) {
//                        R.id.editAppointmentLinearLayout -> {
//                            findNavController().navigate(R.id.action_AppointmentListFragment_to_EditAppointmentFragment, args)
////                            findNavController().navigate(R.id.notificationFragment)
//                        }
                        R.id.viewLinearLayout -> {
                            findNavController().navigate(R.id.action_NewsMVVMListFragment_to_DescriptionFragment, args)
                        }
//                        R.id.mainLinearLayout -> {
//                            findNavController().navigate(R.id.action_AppointmentListFragment_to_LiveTruckingFragment, args)
//                        }

                    }




                }
            } else {
                binding.recyclerView.adapter = recyclerAdapter
            }

        }

    }

}
