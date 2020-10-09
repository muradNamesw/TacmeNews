package com.tacme.news.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

abstract class ParentChildListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private var hasFooter: Boolean = false

    private var itemTypes: Array<Int>?                  = arrayOf()
    private var itemsList: ArrayList<Map<String, Any>>? = arrayListOf()

    fun swapItems(newItems: ArrayList<Map<String, Any>>?, footer: Boolean = false) {

        hasFooter = footer

        newItems?.let {

            //this.importDecs.clear();
            //this.importDecs.addAll( pImportDecs );

            itemsList = newItems

            buildItemTypes()

            notifyDataSetChanged()
        }
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }

        fun bindItem(pos: Int){

        }

    }

    open inner class MainItemViewHolder(itemView: View) : ViewHolder(itemView)

    open inner class SubItemViewHolder(itemView: View)  : ViewHolder(itemView)

    open inner class FooterViewHolder(itemView: View)   : ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{

        val v: View

        return if (viewType == FOOTER_VIEW) {

            //v = LayoutInflater.from(parent.context).inflate(R.layout.list_footer_item, parent, false)
            v = getFooterView()

            FooterViewHolder(v)

        } else if (viewType == MAIN_ITEM_VIEW) {

            //v = LayoutInflater.from(parent.context).inflate(R.layout.list_main_item, parent, false)
            v = getMainView()

            MainItemViewHolder(v)

        } else { // SUB_ITEM_VIEW

            //v = LayoutInflater.from(parent.context).inflate(R.layout.list_sub_item, parent, false)
            v = getSubView()

            SubItemViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        try {

            if (holder is SubItemViewHolder) {

                val viewHolder = holder as SubItemViewHolder

                viewHolder.bindItem(position)

            } else if (holder is MainItemViewHolder) {

                val viewHolder = holder as MainItemViewHolder

                viewHolder.bindItem(position)

            } else if (holder is FooterViewHolder) {

                val viewHolder = holder as FooterViewHolder

                viewHolder.bindItem(position)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun getMainView():   View
    abstract fun getSubView():    View
    abstract fun getFooterView(): View

    private fun buildItemTypes() {

        var arrayList = arrayListOf<Int>()

        itemsList?.forEach{ item ->

            var subs = item["subs"] as? ArrayList<*>

            arrayList.add(MAIN_ITEM_VIEW)

            val subArray = Array(subs?.size ?: 0 ) {
                SUB_ITEM_VIEW
            }

            arrayList.addAll( subArray )

            if( hasFooter ){
                arrayList.add( FOOTER_VIEW )
            }

        }

        itemTypes = arrayList.toTypedArray()
    }

    override fun getItemCount(): Int {

        var mainItemsCount = itemsList?.size ?: 0
        var subItemsCount  = 0

        itemsList?.forEach{ item ->

            var subs = item["subs"] as? ArrayList<*>

            subItemsCount += subs?.size ?: 0

            if( hasFooter ){
                subItemsCount += 1
            }

        }

        mainItemsCount += subItemsCount

        return mainItemsCount

    }

    override fun getItemViewType(position: Int): Int {

        return itemTypes?.get(position) ?: super.getItemViewType(position)

    }

    companion object {

        private const val MAIN_ITEM_VIEW = 1
        private const val SUB_ITEM_VIEW  = 2
        private const val FOOTER_VIEW    = 3

    }

    abstract fun bindItem(pos: Int)
}