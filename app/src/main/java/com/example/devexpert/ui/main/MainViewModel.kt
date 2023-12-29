package com.example.devexpert.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    /**
     * Por practica de codigo limpio
     * Se recomienda crear el livedata privado y después crear uno publico
     * el publico provee del privado encapsulando para hacerlo más restringido
     */
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> get() = _progressLiveData

    private val _itemsLiveData = MutableLiveData<List<MediaItem>>()
    val itemsLiveData: LiveData<List<MediaItem>> get() = _itemsLiveData

    private val _navigateToDetail = MutableLiveData<Int>()
    val navigateToDetail: LiveData<Int> get() = _navigateToDetail


    fun updateItems(filter: Filter = Filter.None){
        viewModelScope.launch {
            _progressLiveData.value = true
            _itemsLiveData.value = withContext(Dispatchers.IO){ getFilteredItems(filter) }
            _progressLiveData.value = false
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
        _navigateToDetail.value = mediaItem.id
    }
}