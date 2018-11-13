package com.diegop.appoffline.utils

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()
    class Loading<T>(val data: T?) : Resource<T>()
    class Error<T>(val message: String, val data: T?) : Resource<T>()
}
