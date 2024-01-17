package com.example.devexpert.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider
import com.example.devexpert.data.MediaProviderImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val mediProvider: MediaProvider ,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _title = MutableLiveData<String>()
    val title : LiveData<String> get() = _title

    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData : LiveData<Boolean> get() = _progressLiveData

    private val _url = MutableLiveData<String>()
    val url : LiveData<String> get() = _url

    private val _videoIndicatorVisible = MutableLiveData<Boolean>()
    val videoIndicatorVisible : LiveData<Boolean> get() = _videoIndicatorVisible

    fun onCreate(itemId: Int){
        viewModelScope.launch {
            _progressLiveData.value = true
            val items = withContext(ioDispatcher) { mediProvider.getItems() }
            val item = items.firstOrNull{ it.id == itemId }

            item?.let {
                _title.value = item.title
                _url.value = item.url
                _videoIndicatorVisible.value  =  when(item.type){
                    MediaItem.Type.PHOTO -> false
                    MediaItem.Type.VIDEO -> true
                }
            }
            _progressLiveData.value = false
        }
    }

}