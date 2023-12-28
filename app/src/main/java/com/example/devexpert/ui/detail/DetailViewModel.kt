package com.example.devexpert.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaProvider
import com.example.devexpert.ui.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: ViewModel() {

    private val _title = MutableLiveData<String>()
    val title : LiveData<String> get() = _title

    private val _url = MutableLiveData<String>()
    val url : LiveData<String> get() = _url

    private val _videoIndicatorVisible = MutableLiveData<Boolean>()
    val videoIndicatorVisible : LiveData<Boolean> get() = _videoIndicatorVisible

    fun onCreate(itemId: Int){
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) { MediaProvider.getItems() }
            val item = items.firstOrNull{ it.id == itemId }

            item?.let {
                _title.value = item.title
                _url.value = item.url
                _videoIndicatorVisible.value  =  when(item.type){
                    MediaItem.Type.PHOTO -> false
                    MediaItem.Type.VIDEO -> true
                }
            }
        }
    }

}