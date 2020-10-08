package com.grdj.devigettest.api

import com.google.gson.GsonBuilder
import com.grdj.devigettest.domain.RedditPost
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RedditApiService {
    private var base_url = "https://raw.githubusercontent.com/"

    val gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(RedditApi::class.java)

    suspend fun getRedditPost() : Response<RedditPost> = api.getRedditPost()
}