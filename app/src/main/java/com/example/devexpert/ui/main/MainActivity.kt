package com.example.devexpert.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.devexpert.ui.detail.DetailActivity
import com.example.devexpert.R
import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.databinding.ActivityMainBinding
import com.example.devexpert.ui.startActivity

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel: MainViewModel

/**
 * @lazy utilizado para posponer la inicialización de MediaAdapter
 * hasta que se llame por primera vez a Adapter.
 *
 *  No es necesario iniciarlo hasta que realmente se necesita.
 */
    private val adapter by lazy {
        MediaAdapter() {mediaItem->
            viewModel.onMediaItemClicked(mediaItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Pasamos @this para indicar que en esta actividad se almacenará el viewModel
         * la instancia de MainViewModel seguira el siclo de vida de MainActivity
         */
        viewModel = ViewModelProvider(this).get()
        viewModel.progressLiveData.observe(this, Observer {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.itemsLiveData.observe(this, Observer{ adapter.items = it })
        viewModel.navigateToDetail.observe(this, Observer {
            startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it)
        })

        binding.recycler.adapter = adapter
        viewModel.updateItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter =  when(item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }

        viewModel.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }
}