package com.tacme.news.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.apprikot.listable.interfaces.Listable
import com.apprikot.listable.model.HolderClass

/**
 * Created by Murad Adnan on 2019-11-25.
 */
 class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    var items: ArrayList<Listable> = arrayListOf()

    var onClickItem: ((position: Int, view: View, anyObject: Any) -> Unit)? = null

    constructor(items: ArrayList<Listable>) {
        this.items = items
    }


    open class BaseViewHolder : RecyclerView.ViewHolder {

        var listableItem: Listable? = null
        var binding: ViewDataBinding? = null

        var onClickItem: ((position: Int, view: View, anyObject: Any) -> Unit)? = null

        constructor(binding: ViewDataBinding) : super(binding.root) {
            this.binding = binding
        }

        open fun onBindView(listableItem: Listable) {
            this.listableItem = listableItem
        }

        open fun attachClickListener(vararg views: View) {
            for (view in views) {

                view.setOnClickListener {

                    listableItem?.let { item ->
                        onClickItem?.invoke(adapterPosition, it, item)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        var holderClass: HolderClass? = null
        for (item in items) {
            if (item.listItemType.layoutResId == viewType) {
                holderClass = item.listItemType
                break
            }
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            holderClass?.layoutResId ?: 0,
            parent,
            false
        )

        val constructor = holderClass?.viewHolderClass?.getConstructor(ViewDataBinding::class.java)

        val baseViewHolder = (constructor?.newInstance(binding) as BaseViewHolder)

        baseViewHolder.onClickItem = onClickItem

        return baseViewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].listItemType.layoutResId
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindView(items[position])
    }

    fun swapItems(newItems: ArrayList<Listable>?) {

        newItems?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    inner class OnClickCallback {

        fun onClick(view: View, obj: Listable) {

            val context = view.context

            //TODO must set to the true index
            onClickItem?.invoke(0, view, obj)

            //this@ImportDecListItemAdapter.onClickItem?.invoke(view, importDec)

        }
    }

}