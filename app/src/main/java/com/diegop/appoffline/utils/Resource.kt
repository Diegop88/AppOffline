package com.diegop.appoffline.utils

class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)
        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, data, message)
    }
}
