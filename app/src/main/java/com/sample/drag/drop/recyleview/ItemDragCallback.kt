package com.sample.drag.drop.recyleview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemDragCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, ItemTouchHelper.ACTION_STATE_IDLE)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val adapter = recyclerView.adapter
        if (adapter is DragAdapterListener) adapter.onItemMoved(
            viewHolder.adapterPosition,
            target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder is DraggedViewHolder) viewHolder.onItemSelected()
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val adapter = recyclerView.adapter
        if (adapter is DragAdapterListener) adapter.onDragFinished()
        if (viewHolder is DraggedViewHolder) viewHolder.onItemDeselected()
    }

    interface DragAdapterListener {
        fun onItemMoved(fromIndex: Int, toIndex: Int)
        fun onDragFinished()
    }

    interface DraggedViewHolder {
        fun onItemSelected()
        fun onItemDeselected()
    }
}