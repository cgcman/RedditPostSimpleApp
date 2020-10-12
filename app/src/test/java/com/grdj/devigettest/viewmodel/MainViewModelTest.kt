package com.grdj.devigettest.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.grdj.devigettest.CoroutineTestRule
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.repository.Repository
import com.grdj.devigettest.resources.ResourcesProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest{
    private lateinit var application: Application
    private lateinit var repository: Repository
    private lateinit var resourcesProvider: ResourcesProvider

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Rule @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        application = mock(Application::class.java)
        repository = mock(Repository::class.java)
        resourcesProvider = mock(ResourcesProvider::class.java)
    }

    @Test
    fun `when get Post from repository is call and return a NETWORK ERROR then show a network Error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val SUT = spy(getMainViewModel())
        val result = ResultWrapper.NetworkError
        val error = MutableLiveData<Boolean>()
        val expected = true
        error.value = expected
        `when`(SUT.isDataFetch()).thenReturn(false)
        `when`(repository.getRedditPost()).thenReturn(result)
        SUT.getRedditPost()
        verify(SUT, times(1)).showNetworkError()
        assertEquals(error.getValue(), expected)
    }

    @Test
    fun `when get Post from repository is call and return a GenericError ERROR then show a Generic Error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val SUT = spy(getMainViewModel())
        val result = ResultWrapper.GenericError(999)
        val error = MutableLiveData<Boolean>()
        val expected = true
        error.value = expected
        `when`(SUT.isDataFetch()).thenReturn(false)
        `when`(repository.getRedditPost()).thenReturn(result)
        SUT.getRedditPost()
        verify(SUT, times(1)).showGenericError(result)
        assertEquals(error.getValue(), expected)
    }

    @Test
    fun `when get Post from repository is call and return SUCCESS then Populate the View`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val SUT = spy(getMainViewModel())
        val mockList = ArrayList<Children>(arrayListOf())
        val result = ResultWrapper.Success(mockList)
        val data = MutableLiveData<List<Children>>()
        val expected = mockList
        data.value = expected
        `when`(SUT.isDataFetch()).thenReturn(false)
        `when`(repository.getRedditPost()).thenReturn(result)
        SUT.getRedditPost()
        verify(SUT, times(1)).populateUI(mockList)
        assertEquals(data.getValue(), expected)
    }

    fun getMainViewModel() : MainViewModel {
        return MainViewModel(
            application,
            repository,
            resourcesProvider
        )
    }
}