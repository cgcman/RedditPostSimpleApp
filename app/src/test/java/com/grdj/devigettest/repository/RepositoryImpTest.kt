package com.grdj.devigettest.repository

import android.content.Context
import androidx.room.Room
import com.grdj.devigettest.CoroutineTestRule
import com.grdj.devigettest.api.ErrorManagerHelper
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.db.PostDB
import com.grdj.devigettest.db.PostDao
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.util.network.NetworkManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.io.IOException

@ExperimentalCoroutinesApi
class RepositoryImpTest{
    private lateinit var service: RedditApiService
    private lateinit var networkManager: NetworkManager
    private lateinit var postDao: PostDao
    private lateinit var postDB: PostDB
    private lateinit var contextMock: Context
    private lateinit var errorManagerHelper: ErrorManagerHelper

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setup(){
        service  = mock(RedditApiService::class.java)
        networkManager = mock(NetworkManager::class.java)
        postDao = mock(PostDao::class.java)
        errorManagerHelper = mock(ErrorManagerHelper::class.java)
        contextMock = mock(Context::class.java)
        postDB = mock(PostDB::class.java)

        Room.inMemoryDatabaseBuilder(contextMock, PostDB::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        postDB.close()
    }

    @Test
    fun `when getRedditPost is call and there is CONNECTION but there is a NETWORK ERROR then returns network error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = getRepositoryImpl()
        val result = ResultWrapper.NetworkError
        `when`(networkManager.isConnected()).thenReturn(true)
        `when`(service.getRedditPost()).thenReturn(result)
        assertEquals(repository.getRedditPost(), result)
    }

    @Test
    fun `when getRedditPost is call and there is CONNECTION but there is a GenericError ERROR then returns generic error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = getRepositoryImpl()
        val result = ResultWrapper.GenericError(999)
        `when`(networkManager.isConnected()).thenReturn(true)
        `when`(service.getRedditPost()).thenReturn(result)
        assertEquals(repository.getRedditPost(), result)
    }

    @Test
    fun `when getRedditPost is call and there is CONNECTION and there is no ERROR the returns Success`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = getRepositoryImpl()
        val mockList = ArrayList<Children>(arrayListOf())
        val result = ResultWrapper.Success(mockList)
        `when`(networkManager.isConnected()).thenReturn(true)
        `when`(service.getRedditPost()).thenReturn(result)
        assertEquals(repository.getRedditPost(), result)
    }

    @Test
    fun `when getRedditPost is call and there is NO CONNECTION but there is a GenericError ERROR then returns generic error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = getRepositoryImpl()
        `when`(networkManager.isConnected()).thenReturn(false)
        val result = errorManagerHelper.dataCall() {
            throw IllegalStateException()
        }
        assertEquals(repository.getRedditPost(), result)
    }

    @Test
    fun `when getRecipesList is call and there is NO CONNECTION and there is no ERROR the returns Success`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = getRepositoryImpl()
        `when`(networkManager.isConnected()).thenReturn(false)
        val mockList = ArrayList<Children>(arrayListOf())
        val result = errorManagerHelper.dataCall() {
            ResultWrapper.Success(mockList)
        }
        assertEquals(repository.getRedditPost(), result)
    }

    fun getRepositoryImpl() : RepositoryImp{
        return RepositoryImp(
            service,
            networkManager,
            postDao,
            errorManagerHelper
        )
    }
}