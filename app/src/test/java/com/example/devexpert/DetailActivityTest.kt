package com.example.devexpert

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.devexpert.ui.detail.DetailViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailActivityTest {

    private lateinit var viewModel: DetailViewModel
    private val fakeMediaProvider = FakeMediaProviderNotNull()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = DetailViewModel(fakeMediaProvider, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `verification of livedata title and url`() =coroutinesTestRule.testDispatcher.runBlockingTest {

        val observer1 = mock<Observer<String>>()
        val observer2 = mock<Observer<String>>()
        viewModel.title.observeForever(observer1)
        viewModel.url.observeForever(observer2)

        viewModel.onCreate(1)

        fakeMediaProvider.getItems().let {
            verify(observer1).onChanged(it.first().title)
            verify(observer2).onChanged(it.first().url)
        }
    }

    @Test
    fun `pregress is set visible when progressLiveData value changes`(){

        val observer = mock<Observer<Boolean>>()
        viewModel.progressLiveData.observeForever(observer)

        viewModel.onCreate(1)

        verify(observer).onChanged(true)
    }


}