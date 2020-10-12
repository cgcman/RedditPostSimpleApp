package com.grdj.devigettest.repository

import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.domain.RedditPost
import com.grdj.devigettest.domain.children.Data
import retrofit2.Response

interface Repository {
    suspend fun getRedditPost(): ResultWrapper<List<Children>?>
    suspend fun deletePost(data: Data): ResultWrapper<Any>
}