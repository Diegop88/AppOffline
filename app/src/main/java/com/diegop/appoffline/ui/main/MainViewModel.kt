package com.diegop.appoffline.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Repo
import com.diegop.appoffline.domain.usecase.repo.GetReposByUser
import com.diegop.appoffline.utils.Status

class MainViewModel(private val getReposByUser: GetReposByUser) : ViewModel() {

    val userData = MutableLiveData<List<Repo>>()
    val errorData = MutableLiveData<String>()

    fun getRepo(user: String) {
        getReposByUser.getReposByUser(user).subscribe {
            when (it.status) {
                Status.SUCCESS -> userData.value = it.data
                Status.ERROR -> {
                    errorData.value = it.message
                    userData.value = it.data
                }
                Status.LOADING -> {
                }
            }
        }
    }

    class Factory(private val getReposByUser: GetReposByUser) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(getReposByUser) as T
    }
}
