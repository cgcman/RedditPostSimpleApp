package com.grdj.devigettest.network

import com.grdj.devigettest.CoroutineTestRule
import com.grdj.devigettest.api.ErrorManagerHelperImpl
import com.grdj.devigettest.api.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ErrorManagerHelperImplTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun `when results returns successfully then it should emit the result as success`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val SUT = getNetworkInteractor()
            val lambdaResult = true
            val result = SUT.dataCall { lambdaResult }
            assertEquals(ResultWrapper.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when results throws IOException then it should emit the result as NetworkError`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val SUT = getNetworkInteractor()
            val result = SUT.dataCall() { throw IOException() }
            assertEquals(ResultWrapper.NetworkError, result)
        }
    }

    @Test
    fun `when results throws unknown exception then it should emit GenericError`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val SUT = getNetworkInteractor()
            val result = SUT.dataCall() {
                throw IllegalStateException()
            }
            assertEquals(ResultWrapper.GenericError(), result)
        }
    }

    fun getNetworkInteractor() : ErrorManagerHelperImpl {
        return ErrorManagerHelperImpl(coroutinesTestRule.testDispatcherProvider)
    }
}