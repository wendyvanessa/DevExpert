package com.example.devexpert.ui.main

import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Presenter contiene la logica
 */

class MainPresenter(private val view: View, private  val scope: CoroutineScope) {

    interface View{
        fun setProgressVisible(visible: Boolean)
        fun updateItems(items: List<MediaItem>)
        fun navigateToDetail(id: Int)
    }

    fun updateItems(filter: Filter = Filter.None){
        scope.launch {
            view.setProgressVisible(true)
            val items = withContext(Dispatchers.IO){ getFilteredItems(filter) }
            view.updateItems(items)
            view.setProgressVisible(false)
        }
    }

    /**
     * @filter es el tipo de dato ->
     *      Filter.ByType(MediaItem.Type.PHOTO)
     *      Filter.ByType(MediaItem.Type.VIDEO)
     *      Filter.None
     *
     * Definimos que mostrar al usuario dependiendo del tipo de dato que entre
     */
    private  fun getFilteredItems(filter: Filter): List<MediaItem>{
        return MediaProvider.getItems().let { media ->
            when(filter){
                Filter.None-> media
                is Filter.ByType -> media.filter { it.type == filter.type }
            }
        }
    }

    fun onMediaItemClicked(mediaItem: MediaItem){
        view.navigateToDetail(mediaItem.id)
    }
}