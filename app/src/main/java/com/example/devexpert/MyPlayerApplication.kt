package com.example.devexpert

import android.app.Application
import com.example.devexpert.data.MediaProvider
import com.example.devexpert.data.MediaProviderImpl
import com.example.devexpert.ui.detail.DetailViewModel
import com.example.devexpert.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


class MyPlayerApplication : Application(){

    val appModule = module {
        single<MediaProvider> { MediaProviderImpl }
        single(named("ioDispatcher")) { Dispatchers.IO }
    }

    val scopeModule = module {
        viewModel { MainViewModel(get(), get(named("ioDispatcher"))) }
        viewModel { DetailViewModel(get(), get(named("ioDispatcher"))) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyPlayerApplication)
            modules(appModule, scopeModule)
        }
    }
}