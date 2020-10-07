package com.grdj.devigettest.api

import com.grdj.devigettest.domain.RedditPost
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditApiService {
    private var base_url = "https://www.reddit.com/"
    private val api = Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RedditApi::class.java)

    suspend fun getRedditPost() : Response<RedditPost> = api.getRedditPost()
}