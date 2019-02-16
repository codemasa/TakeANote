package com.codemasa.codyabe.takeanote.helpers

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

public class ItemTouchHelperCallback(adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback(){

    internal var adapter: ItemTouchHelperAdapter

    init{
        this.adapter = adapter
    }

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)

        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if(viewHolder.itemViewType != target.itemViewType){
            return false
        }
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}