package com.grdj.devigettest.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.domain.children.Data
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

    var redditPostList = MutableLiveData<List<Children>>()
    var error = MutableLiveData<Boolean>()
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
            val response = repository.deleteReddit(data)
            when (response) {
                is ResultWrapper.GenericError -> showDeleteDBError(response)
            }
        }
    }

    fun populateUI(response: List<Children>?){
        redditPostList.value = response
        dataIsFetch = true
    }

    fun showNetworkError(){
        error.value = true
        Toast.makeText(getApplication(), resourcesProvider.getApiError(), Toast.LENGTH_SHORT).show()
        Timber.d("RESPONSE, NETWORK ERROR")
    }

    fun showGenericError(response: Any?) {
        error.value = true
        Toast.makeText(getApplication(), resourcesProvider.getApiError(), Toast.LENGTH_SHORT).show()
        Timber.d("RESPONSE, $response")
    }

    fun showDeleteDBError(response: Any?) {
        error.value = true
        Toast.makeText(getApplication(), resourcesProvider.getDeleteDBErrorr(), Toast.LENGTH_SHORT).show()
        Timber.d("RESPONSE, $response")
    }

    fun persistData(post: Children){
        lastSaveData.value = post
    }
}