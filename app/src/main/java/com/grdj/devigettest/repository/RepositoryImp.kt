package com.grdj.devigettest.repository

import com.grdj.devigettest.api.NetworkHelper
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.RedditPost
import retrofit2.Response

class RepositoryImp(
    private val service: RedditApiService,
    private val networkHelper: NetworkHelper) : Repository  {

    override suspend fun getRedditPost(): ResultWrapper<Response<RedditPost>> {
        return networkHelper.apiCall() {
            service.getRedditPost()
        }
    }
}