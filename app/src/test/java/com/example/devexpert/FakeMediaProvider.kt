package com.example.devexpert

import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider

class FakeMediaProvider: MediaProvider {
    override fun getItems(): List<MediaItem> = emptyList()
}

class FakeMediaProviderNotNull: MediaProvider {
    override fun getItems(): List<MediaItem> =
        listOf(MediaItem(1,"none","none", MediaItem.Type.PHOTO))
}