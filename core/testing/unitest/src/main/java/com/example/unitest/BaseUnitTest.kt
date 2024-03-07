package com.example.unitest

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseUnitTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineRule()


    @Before
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    open fun setUp() {
        // make annotation work
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
  open  fun tearDown() {
        unmockkAll()
        //mainCoroutineRule.cancel()
    }
}