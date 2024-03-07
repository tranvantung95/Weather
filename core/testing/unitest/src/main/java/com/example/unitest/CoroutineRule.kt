package com.example.unitest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineRule @OptIn(ExperimentalCoroutinesApi::class) constructor(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description) {
        try {
            Dispatchers.setMain(this.testDispatcher)
        }catch (e : Exception){
            println("===${e.message}===")
        }

    }

    override fun finished(description: Description) {
        try {
            Dispatchers.resetMain()
        }catch (e: Exception){
            println("===${e.message}===")
        }
    }
}