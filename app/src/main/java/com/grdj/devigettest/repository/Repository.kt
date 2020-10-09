package com.grdj.devigettest.repository

import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.domain.RedditPost
import retrofit2.Response

interface Repository {
    suspend fun getRedditPost(): ResultWrapper<List<Children>?>
}