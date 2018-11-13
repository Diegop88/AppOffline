package com.diegop.appoffline.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Repo
import com.diegop.appoffline.domain.usecase.repo.GetReposByUser
import com.diegop.appoffline.utils.Resource
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val getReposByUser: GetReposByUser) : ViewModel() {

    private val _userData = MutableLiveData<List<Repo>>()
    val userData: LiveData<List<Repo>>
        get() = _userData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    private val compositeDisposable = CompositeDisposable()

    fun getRepo(user: String) = compositeDisposable.add(getReposByUser.getReposByUser(user).subscribe {
        when (it) {
            is Resource.Success -> _userData.value = it.data
            is Resource.Error -> {
                _errorData.value = it.message
                _userData.value = it.data
            }
            is Resource.Loading -> Unit
        }
    })

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getReposByUser: GetReposByUser) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(getReposByUser) as T
    }
}
