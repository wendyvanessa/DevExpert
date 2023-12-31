package com.example.devexpert.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.devexpert.ui.detail.DetailActivity
import com.example.devexpert.R
import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.databinding.ActivityMainBinding
import com.example.devexpert.ui.getViewModel
import com.example.devexpert.ui.observe
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

        observers()
        binding.recycler.adapter = adapter
        viewModel.onFilterClick()
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

        viewModel.onFilterClick(filter)
        return super.onOptionsItemSelected(item)
    }

    /**
     * Se crea la instancia del MainViewModel por medio de una función de extensión "getViewModel"
     * la cual implementa como parametro la función de extención "observe"
     *
     * Las funciones de extensión con tipos de datos genericos  son utilizadas para
     * eliminar boilerplate del codigo quedando mucho mas legible.
     */
    fun observers(){
        viewModel = getViewModel {
            observe(progressLiveData){
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
            observe(itemsLiveData){ adapter.items = it }
            observe(navigateToDetail) {
                it.getContentIfNotHandled()?.let { itemId ->
                    startActivity<DetailActivity>(DetailActivity.EXTRA_ID to itemId)
                }
            }
        }
    }
}