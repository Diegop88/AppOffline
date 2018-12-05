package com.diegop.appoffline.utils

import android.util.Log
import retrofit2.Response
import java.io.IOException

abstract class NetworkBoundResourceRx<ResultType : Any, RequestType : Any> {

    suspend operator fun invoke(): Resource<ResultType> {
        val local = loadFromDb()
        return if (shouldFetch(local)) {
            val requestData = safeApiCall(call = { fetchFromNetwork() }, errorMessage = "Failed to retrieve from API")
            return when (requestData) {
                is Resource.Success -> {
                    saveCallResult(requestData.data)
                    Resource.Success(loadFromDb())
                }
                is Resource.Error -> {
                    onFetchFailed(requestData.error)
                    Resource.Error(requestData.error, local)
                }
            }.exhaustive
        } else {
            Resource.Success(local)
        }
    }

    private suspend fun fetchFromNetwork(): Resource<RequestType> {
        val response = createCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Resource.Success(data = body)
            }
        }
        return Resource.Error(error = IOException("Failed to retrieve from API ${response.code()} ${response.message()}"), data = null)
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Resource<T>, errorMessage: String): Resource<T> {
        return try {
            call()
        } catch (e: Exception) {
            Resource.Error(IOException(errorMessage, e), null)
        }
    }

    protected open fun onFetchFailed(error: Exception) {
        Log.e("NetworkBound", "Fetch failed ${error.message}")
    }

    protected abstract suspend fun loadFromDb(): ResultType

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Response<RequestType>

    protected abstract suspend fun saveCallResult(data: RequestType?)
}
