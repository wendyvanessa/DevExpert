package com.example.devexpert.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.devexpert.ui.detail.DetailActivity
import com.example.devexpert.R
import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.databinding.ActivityMainBinding
import com.example.devexpert.ui.startActivity

class MainActivity : AppCompatActivity(), MainPresenter.View{

    lateinit var binding: ActivityMainBinding
    private  val mainPresenter = MainPresenter(this,lifecycleScope)

/**
 * @lazy utilizado para posponer la inicialización de MediaAdapter
 * hasta que se llame por primera vez a Adapter.
 *
 *  No es necesario iniciarlo hasta que realmente se necesita.
 */
    private val adapter by lazy {
        MediaAdapter() {mediaItem->
            mainPresenter.onMediaItemClicked(mediaItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter
        mainPresenter.updateItems()
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

        mainPresenter.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    override fun setProgressVisible(visible: Boolean) {
        binding.progress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun updateItems(items: List<MediaItem>) {
        adapter.items = items
    }

    override fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}