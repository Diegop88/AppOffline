package com.diegop.appoffline.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResourceRx<ResultType, RequestType> @MainThread protected constructor() {

    private val result: Observable<Resource<ResultType>>

    init {
        result = Observable.create { result ->
            result.onNext(Resource.loading(null))
            val data = loadFromDb()
            if (shouldFetch(data)) {
                fetchFromNetwork(result, data)
            } else {
                result.onNext(Resource.success(data))
                result.onComplete()
            }
        }
    }

    private fun fetchFromNetwork(result: ObservableEmitter<Resource<ResultType>>, data: ResultType) {
        val resource = createCall()
        when (resource.status) {
            Status.SUCCESS -> {
                saveCallResult(resource.data)
                val newData = loadFromDb()
                result.onNext(Resource.success(newData))
                result.onComplete()
            }
            Status.ERROR -> {
                onFetchFailed()
                result.onNext(Resource.error(resource.message, data))
                result.onComplete()
            }
            Status.LOADING -> result.onNext(Resource.loading(data))
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
