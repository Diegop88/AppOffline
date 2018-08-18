package com.diegop.appoffline.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}

val Context.networkInfo: NetworkInfo? get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
