package com.tacme.news.view.viewholders

import androidx.databinding.ViewDataBinding
import com.apprikot.listable.interfaces.Listable
import com.squareup.picasso.Picasso
import com.tacme.news.R
import com.tacme.news.databinding.ListNewsItemMvpBinding
import com.tacme.news.model.mvp.NewsMVPListDec

import com.tacme.news.view.adapters.BaseRecyclerAdapter

/**
 * Created by Murad Adnan on 2020-01-14.
 */
class ListMVPNewsDecVH : BaseRecyclerAdapter.BaseViewHolder {

    constructor(binding: ViewDataBinding) : super(binding)

    override fun onBindView(listableItem: Listable) {

        super.onBindView(listableItem)
//        ListImportDecItemBinding
        (binding as ListNewsItemMvpBinding).newsListDec =
            (listableItem as NewsMVPListDec)

            (binding as ListNewsItemMvpBinding).mainLinearLayout.setBackgroundResource(R.drawable.button_rectangle_black);





        binding?.executePendingBindings()

        binding?.root?.let {
            Picasso.get().load((listableItem as NewsMVPListDec).urlToImage).into((binding as ListNewsItemMvpBinding).statusCircleImageView)

            attachClickListener(
                it,
//                (binding as ListAppointmentDecItemBinding).editAppointmentLinearLayout,
                (binding as ListNewsItemMvpBinding).viewLinearLayout,
                (binding as ListNewsItemMvpBinding).mainLinearLayout
            )
        }
    }
}