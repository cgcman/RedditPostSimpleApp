package com.grdj.devigettest.api

import com.grdj.devigettest.domain.RedditPost
import retrofit2.Response
import retrofit2.http.GET

interface RedditApi {
    @GET("/r/all/top.json")
    suspend fun getRedditPost(): Response<RedditPost>
}