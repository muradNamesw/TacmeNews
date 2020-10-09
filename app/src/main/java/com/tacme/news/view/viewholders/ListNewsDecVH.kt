package com.tacme.news.view.viewholders

import androidx.databinding.ViewDataBinding
import com.apprikot.listable.interfaces.Listable
import com.squareup.picasso.Picasso
import com.tacme.news.R
import com.tacme.news.databinding.ListNewsItemMvvmBinding
import com.tacme.news.model.mvvm.NewsListDec
import com.tacme.news.view.adapters.BaseRecyclerAdapter

/**
 * Created by Murad Adnan on 2020-01-14.
 */
class ListNewsDecVH : BaseRecyclerAdapter.BaseViewHolder {

    constructor(binding: ViewDataBinding) : super(binding)

    override fun onBindView(listableItem: Listable) {

        super.onBindView(listableItem)
//        ListImportDecItemBinding
        (binding as ListNewsItemMvvmBinding).newsListDec =
            (listableItem as NewsListDec)

            (binding as ListNewsItemMvvmBinding).mainLinearLayout.setBackgroundResource(R.drawable.button_rectangle_accent);





        binding?.executePendingBindings()

        binding?.root?.let {
            Picasso.get().load((listableItem as NewsListDec).urlToImage).into((binding as ListNewsItemMvvmBinding).statusCircleImageView)

            attachClickListener(
                it,
//                (binding as ListAppointmentDecItemBinding).editAppointmentLinearLayout,
                (binding as ListNewsItemMvvmBinding).viewLinearLayout,
                (binding as ListNewsItemMvvmBinding).mainLinearLayout
            )
        }
    }
}