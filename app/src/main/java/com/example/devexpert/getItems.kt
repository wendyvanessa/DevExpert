package com.example.devexpert

import com.example.devexpert.MediaItem.*

object getItems{
    val ListItems : List<MediaItem> = (1..10).map {
        MediaItem("Title $it"
            ,"https://placekitten.com/200/200?image=$it",
            if(it % 2 == 0) Type.VIDEO else Type.PHOTO)
    }
}