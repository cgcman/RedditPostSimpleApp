package com.grdj.devigettest.api

import com.grdj.devigettest.util.DefaultDispatcherProvider
import com.grdj.devigettest.util.DispatcherProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.withContext
import retrofit2.adapter.rxjava2.HttpException
import java.io.IOException

class ErrorManagerHelperImpl(private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()) : ErrorManagerHelper {

    override suspend fun <T> dataCall(dataCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher.io()) {
            try {
                ResultWrapper.Success(dataCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ResultWrapper.ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ResultWrapper.ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}