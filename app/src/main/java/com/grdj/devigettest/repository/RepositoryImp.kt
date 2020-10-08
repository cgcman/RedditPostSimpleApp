package com.grdj.devigettest.repository

import com.grdj.devigettest.api.ErrorManagerHelper
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.RedditPost
import com.grdj.devigettest.resources.ResourcesProvider
import com.grdj.devigettest.util.network.NetworkManager
import retrofit2.Response

class RepositoryImp(
    private val service: RedditApiService,
    private val errorkErrorHelper: ErrorManagerHelper,
    private val networkManager: NetworkManager,
    private val resourceProvider: ResourcesProvider
) : Repository  {

    override suspend fun getRedditPost(): ResultWrapper<Response<RedditPost>> {
        if(networkManager.isConnected()){
            return errorkErrorHelper.dataCall() {
                service.getRedditPost()
            }
        } else {
            //return ResultWrapper.NetworkError
            //networkManager.notConnectedMessage(resourceProvider.getNotConnectedMessage())
            return errorkErrorHelper.dataCall() {
                service.getRedditPost()
            }
        }
    }
}