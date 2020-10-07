package com.grdj.devigettest.api

interface NetworkHelper {
    suspend fun <T> apiCall(apiCall: suspend () -> T): ResultWrapper<T>
}