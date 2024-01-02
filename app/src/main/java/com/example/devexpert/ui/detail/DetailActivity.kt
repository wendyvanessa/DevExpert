package com.example.devexpert.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.devexpert.databinding.ActivityDetailBinding
import com.example.devexpert.ui.loadUrl
import com.example.devexpert.ui.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_ID = "DetailActivity:id"
    }

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers(binding)
        val itemId = intent.getIntExtra(EXTRA_ID,-1)
        detailViewModel.onCreate(itemId)

    }
    fun observers(binding: ActivityDetailBinding){
        with(detailViewModel){
            observe(title){ supportActionBar?.title = it }
            observe(url){ binding.detailThumb.loadUrl(it) }
            observe(progressLiveData) { binding.progress.visibility = if (it) View.VISIBLE else View.GONE }
            observe(videoIndicatorVisible){
                binding.detailVideoIndicator.visibility = if(it) View.VISIBLE else View.GONE }
        }
    }
}