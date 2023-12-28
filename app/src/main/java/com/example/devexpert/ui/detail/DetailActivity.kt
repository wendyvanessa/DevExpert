package com.example.devexpert.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.devexpert.databinding.ActivityDetailBinding
import com.example.devexpert.ui.loadUrl

class DetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_ID = "DetailActivity:id"
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get()
        detailViewModel.title.observe(this, Observer { supportActionBar?.title = it })
        detailViewModel.url.observe(this, Observer { binding.detailThumb.loadUrl(it) })

        detailViewModel.videoIndicatorVisible.observe(this, Observer {
            binding.detailVideoIndicator.visibility = if(it) View.VISIBLE else View.GONE
        })

        val itemId = intent.getIntExtra(EXTRA_ID,-1)
        detailViewModel.onCreate(itemId)

    }
}