package com.sample.drag.drop.recyleview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sample.drag.drop.recyleview.databinding.ItemSampleBinding
import java.util.*

class SampleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemDragCallback.DragAdapterListener {

    private var data: List<String> = emptyList()

    override fun getItemCount(): Int = data.size

    var onStartDrag: (RecyclerView.ViewHolder) -> Unit = {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SampleViewHolder) {
            holder.bind(data[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SampleViewHolder(parent.inflate(R.layout.item_sample))


    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun onDataMove(from: Int, to: Int) {
        Collections.swap(data, from, to)
        notifyItemMoved(from, to)
    }

    inner class SampleViewHolder(private val binding: ItemSampleBinding) :
        RecyclerView.ViewHolder(binding.root), ItemDragCallback.DraggedViewHolder {

        override fun onItemDeselected() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.white)
            )
            Log.d("test1233", "test12333 item deseleted")
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.black)
            )
            Log.d("test12333", "test--- item selected")
        }

        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: String) {
            binding.tvLabel.text = data
            binding.btnClickToDrag.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) onStartDrag.invoke(this)
                false
            }
            binding.btnClickToDrag.setOnLongClickListener {
                onStartDrag.invoke(this)
                true
            }
        }

    }

    override fun onItemMoved(fromIndex: Int, toIndex: Int) {
        Collections.swap(data, fromIndex, toIndex)
        notifyItemMoved(fromIndex, toIndex)
    }

    override fun onDragFinished() {
        Log.d("test123333", "On Drag finish")
        // To do confirm
    }
}

inline fun <reified T : ViewDataBinding> ViewGroup.inflate(
    @DrawableRes layout: Int
): T {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        layout,
        this,
        false
    )
}
