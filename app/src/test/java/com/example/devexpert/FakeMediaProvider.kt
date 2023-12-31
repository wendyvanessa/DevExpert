package com.example.devexpert

import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider

class FakeMediaProvider: MediaProvider {
    override fun getItems(): List<MediaItem> = emptyList()
}