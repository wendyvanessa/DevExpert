package com.example.devexpert.data

import androidx.annotation.WorkerThread
import com.example.devexpert.data.MediaItem.*

interface MediaProvider {
    fun getItems(): List<MediaItem>
}

object MediaProviderImpl : MediaProvider{

    override fun getItems(): List<MediaItem> {
        Thread.sleep(2000)
        return (1..10).map {
            MediaItem(
                it,
                "Title $it",
                "https://placekitten.com/200/200?image=$it",
                if(it % 3 == 0) Type.VIDEO else Type.PHOTO)
        }
    }
}

