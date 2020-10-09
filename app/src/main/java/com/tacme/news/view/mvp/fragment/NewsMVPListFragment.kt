package com.tacme.news.view.mvp.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apprikot.listable.interfaces.Listable
import com.tacme.news.R
import com.tacme.news.components.listeners.EndlessRecyclerViewScrollListener
import com.tacme.news.databinding.FragmentMvpNewsListBinding
import com.tacme.news.model.mvp.NewsMVPListDec
import com.tacme.news.presenter.mvp.NewsMVPListPresenter
import com.tacme.news.view.adapters.BaseRecyclerAdapter
import com.tacme.news.view.base.RootFragment
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewsMVPListFragment : RootFragment(), NewsMVPListPresenter.INewsList {


    private var mPreseneter: NewsMVPListPresenter? = null
    private lateinit var binding: FragmentMvpNewsListBinding

    private var dialog: Dialog? = null

    private var recyclerAdapter: BaseRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mvp_news_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindPresenter()

//        bindAdapter()

        findViews()


    }

    private fun bindPresenter() {

        mPreseneter = NewsMVPListPresenter(this)
        mPreseneter!!.loadInitial(activity)
    }


    private fun findViews() {



        val linearLayoutManager = LinearLayoutManager(this.activity)

        binding.recyclerView?.layoutManager = linearLayoutManager
        binding.recyclerView?.setHasFixedSize(true)




        // SwipeRefreshLayout
        binding.swipeRefreshLayout?.setOnRefreshListener { mPreseneter?.loadInitial(activity) }

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

                mPreseneter?.loadNext(activity)

            }
        })




    }

    private fun bindAdapter(itemsList: MutableLiveData<ArrayList<Listable>>) {

        activity?.applicationContext?.let {

            if (recyclerAdapter == null) {
                recyclerAdapter = BaseRecyclerAdapter(itemsList?.value ?: arrayListOf())

                binding.recyclerView.adapter = recyclerAdapter

                recyclerAdapter?.onClickItem = { _, viewId, anyObject ->

                    val args = Bundle()

                    if (anyObject is NewsMVPListDec) {
                        args.putString(RootFragment.PARAM_REF_NO_KEY, anyObject.description)
                    }


                    when (viewId?.id) {
//                        R.id.editAppointmentLinearLayout -> {
//                            findNavController().navigate(R.id.action_AppointmentListFragment_to_EditAppointmentFragment, args)
////                            findNavController().navigate(R.id.notificationFragment)
//                        }
                        R.id.viewLinearLayout -> {
                            findNavController().navigate(R.id.action_NewsMVPListFragment_to_DescriptionFragment, args)
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

    override fun success(itemsList: MutableLiveData<ArrayList<Listable>>) {
        bindAdapter(itemsList)
        recyclerAdapter?.swapItems(itemsList?.value)
        binding.swipeRefreshLayout.isRefreshing = false
    }


}