package com.grdj.devigettest.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.RedditPost
import com.grdj.devigettest.repository.Repository
import com.grdj.devigettest.resources.ResourcesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MainViewModel (
    application: Application,
    private val repository: Repository,
    private val resourcesProvider: ResourcesProvider
): AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    var redditPostList = MutableLiveData<RedditPost>()
    var error = MutableLiveData<Boolean>()
    var dataIsFetch = false

    fun clearDataFetch(fetch: Boolean){
        dataIsFetch = fetch
    }

    fun getRedditPost(){
        if(!dataIsFetch){
            launch {
                val response = repository.getRedditPost()
                when (response) {
                    is ResultWrapper.NetworkError -> showNetworkError()
                    is ResultWrapper.GenericError -> showGenericError(response)
                    is ResultWrapper.Success -> populateUI(response.value.body())
                }
            }
        }
    }

    private fun populateUI(response: RedditPost?){
        redditPostList.value = response
        dataIsFetch = true
    }

    private fun showNetworkError(){
        error.value = true
        Toast.makeText(getApplication(), resourcesProvider.getApiError(), Toast.LENGTH_SHORT).show()
        Timber.d("RESPONSE, NETWORK ERROR")
    }

    private fun showGenericError(response: Any?) {
        error.value = true
        Toast.makeText(getApplication(), resourcesProvider.getApiError(), Toast.LENGTH_SHORT).show()
        Timber.d("RESPONSE, $response")
    }
}