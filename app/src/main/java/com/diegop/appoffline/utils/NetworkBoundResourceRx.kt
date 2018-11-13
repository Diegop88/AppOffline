package com.diegop.appoffline.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResourceRx<ResultType, RequestType> @MainThread protected constructor() {

    private val result: Observable<Resource<ResultType>>

    init {
        result = Observable.create { result ->
            result.onNext(Resource.Loading(null))
            val data = loadFromDb()
            if (shouldFetch(data)) {
                fetchFromNetwork(result, data)
            } else {
                result.onNext(Resource.Success(data))
                result.onComplete()
            }
        }
    }

    private fun fetchFromNetwork(result: ObservableEmitter<Resource<ResultType>>, data: ResultType) {
        val resource = createCall()
        when (resource) {
            is Resource.Success -> {
                saveCallResult(resource.data)
                val newData = loadFromDb()
                result.onNext(Resource.Success(newData))
                result.onComplete()
            }
            is Resource.Error -> {
                onFetchFailed()
                result.onNext(Resource.Error(resource.message, data))
                result.onComplete()
            }
            is Resource.Loading -> result.onNext(Resource.Loading(data))
        }
    }

    protected open fun onFetchFailed() {
        Log.e("NetworkBound", "Fetch failed")
    }

    fun asObservable(): Observable<Resource<ResultType>> = result.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())

    @WorkerThread
    protected abstract fun saveCallResult(data: RequestType?)

    @MainThread
    protected abstract fun loadFromDb(): ResultType

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun createCall(): Resource<out RequestType>
}
