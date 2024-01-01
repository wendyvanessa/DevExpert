package com.example.devexpert

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * @TestWatcher() nos permite definir una regla
 * nos permite obserbar los estados o cambios en el test
 * para definir que hacer en cada punto
 */

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        /**
         * La libreria de testing de corrutinas permite crear el mainDistPatcher en el que corre
         * la corrutina ya que en el test no puede correr en el hilo principal
         */
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}