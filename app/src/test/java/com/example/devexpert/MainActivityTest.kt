package com.example.devexpert

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.devexpert.data.Filter
import com.example.devexpert.data.MediaItem
import com.example.devexpert.data.MediaItem.Type
import com.example.devexpert.ui.Event
import com.example.devexpert.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Estructura AAA
 *
 * parte 1: (Arrange -> Preparar) Crear objetos para ejecutar el test.
 * parte 2: (Act -> Actuar) Actuamos, realizamos la acción sobre la que queremos. testear
 * parte 3: (Assert -> Afirmar) Comprobar que esa parte de codigo esta funcionando.
 */

@ExperimentalCoroutinesApi
class MainActivityTest {

    private lateinit var viewModel: MainViewModel
    private val fakeMediaProvider = FakeMediaProvider()
    private val testDispatcher = TestCoroutineDispatcher()

    /**
     * @rule es una clase que nos permite reutilizar codigo antes y despues del test
     * no tenemos que escribirlo cada vez que creemos una nueva clase de test.
     *
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        /**
         * La libreria de testing de corrutinas permite crear el mainDistPatcher en el que corre
         * la corrutina ya que en el test no puede correr en el hilo principal
         */
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(fakeMediaProvider, testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun`pregress is set visible when progressLiveData value changes`() =
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            viewModel.progressLiveData.observeForever(observer)

            viewModel.onFilterClick(Filter.None)

            verify(observer).onChanged(true)
    }

    @Test
    fun `navigates to detail when onItemClicked`() = testDispatcher.runBlockingTest {
        val observer = mock<Observer<Event<Int>>>()

        viewModel.navigateToDetail.observeForever(observer)

        viewModel.onMediaItemClicked(MediaItem(1,"","", Type.PHOTO))

        verify(observer).onChanged(Event(1))
    }

    @Test
    fun `updates items onfilterClicked`(){
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<List<MediaItem>>>()

            viewModel.itemsLiveData.observeForever(observer)

            viewModel.onFilterClick()

            verify(observer).onChanged(fakeMediaProvider.getItems())
        }
    }
}