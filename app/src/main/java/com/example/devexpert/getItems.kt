package com.example.devexpert

import androidx.annotation.WorkerThread
import com.example.devexpert.MediaItem.*

object MediaProvider {
    /**
     * @WorkerThread esta anotación es para avisar que esta tarea
     * no debe ser ejecutada en el hilo principal
     */
    @WorkerThread
    fun getItems(): List<MediaItem> {
        Thread.sleep(2000)
        return (1..10).map {
            MediaItem("Title $it"
                ,"https://placekitten.com/200/200?image=$it",
                if(it % 2 == 0) Type.VIDEO else Type.PHOTO)
        }
    }
}

