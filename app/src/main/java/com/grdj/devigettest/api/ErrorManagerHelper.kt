package com.grdj.devigettest.api

interface ErrorManagerHelper {
    suspend fun <T> dataCall(dataCall: suspend () -> T): ResultWrapper<T>
}