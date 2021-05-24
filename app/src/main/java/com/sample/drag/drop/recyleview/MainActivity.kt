package com.sample.drag.drop.recyleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sampleAdapter = SampleAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_listing.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = sampleAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        sampleAdapter.updateData(getSampleData())

        swipe_refresh_layout.setOnRefreshListener {
            sampleAdapter.updateData(getSampleData())
            swipe_refresh_layout.isRefreshing = false
        }
        ItemTouchHelper(ItemDragCallback()).apply {
            sampleAdapter.onStartDrag = { this.startDrag(it) }
            attachToRecyclerView(rv_listing)
        }
    }

    private fun getSampleData(): List<String> {
        val list = arrayListOf<String>()
        for (char in 'a'..'z') {
            list.add(char.toString())
        }
        return list
    }

}
