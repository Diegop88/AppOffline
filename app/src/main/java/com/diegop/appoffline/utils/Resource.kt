package com.diegop.appoffline.utils

sealed class Resource<out T : Any> {
    class Success<out T : Any>(val data: T?) : Resource<T>()
    class Error<out T : Any>(val error: Exception, val data: T?) : Resource<T>()
}
