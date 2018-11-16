package com.diegop.appoffline.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Repo
import com.diegop.appoffline.domain.usecase.repo.GetReposByUser
import com.diegop.appoffline.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val getReposByUser: GetReposByUser) : ViewModel() {

    private val _userData = MutableLiveData<List<Repo>>()
    val userData: LiveData<List<Repo>>
        get() = _userData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    fun getRepo(user: String) = GlobalScope.launch(Dispatchers.IO) {
        val response = getReposByUser(user)
        withContext(Dispatchers.Main) {
            when (response) {
                is Resource.Success -> _userData.value = response.data
                is Resource.Error -> {
                    _errorData.value = response.error.message
                    _userData.value = response.data
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getReposByUser: GetReposByUser) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(getReposByUser) as T
    }
}
