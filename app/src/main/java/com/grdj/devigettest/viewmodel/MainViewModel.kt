package com.grdj.devigettest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.domain.children.Data
import com.grdj.devigettest.repository.Repository
import com.grdj.devigettest.resources.ResourcesProvider
import com.grdj.devigettest.util.Constants.ZERO
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

    var redditPostList = MutableLiveData<List<Children>>()
    var error = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var dataIsFetch = false
    var lastSaveData = MutableLiveData<Children>()

    fun isDataFetch() : Boolean = dataIsFetch

    fun getRedditPost(){
        if(!isDataFetch()){
            launch {
                val response = repository.getRedditPost()
                when (response) {
                    is ResultWrapper.NetworkError -> showNetworkError()
                    is ResultWrapper.GenericError -> showGenericError(response)
                    is ResultWrapper.Success -> {
                        populateUI(response.value)
                    }
                }
            }
        }
    }

    fun deletePost(data : Data){
        launch {
            val response = repository.deletePost(data)
            when (response) {
                is ResultWrapper.GenericError -> showDeleteDBError(response)
                is ResultWrapper.Success -> showDeleteDBSuccess()
            }
        }
    }

    fun populateUI(response: List<Children>?){
        redditPostList.value = response
        dataIsFetch = true
        if(!response!!.size.equals(ZERO)){
            persistData(response.get(0))
        }
    }

    fun showNetworkError(){
        error.value = true
        message.value = resourcesProvider.getApiError()
        Timber.d("RESPONSE, NETWORK ERROR")
    }

    fun showGenericError(response: Any?) {
        error.value = true
        message.value = resourcesProvider.getApiError()
        Timber.d("RESPONSE, $response")
    }

    fun showDeleteDBError(response: Any?) {
        error.value = true
        resourcesProvider.getDeleteDBErrorr()
        Timber.d("RESPONSE, $response")
    }

    fun showDeleteDBSuccess() {
        message.value = resourcesProvider.getDeleteDBSuccess()
    }

    fun persistData(post: Children){
        lastSaveData.value = post
    }
}