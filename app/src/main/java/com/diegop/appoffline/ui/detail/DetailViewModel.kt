package com.diegop.appoffline.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Issue
import com.diegop.appoffline.domain.usecase.issue.GetIssuesByRepo
import com.diegop.appoffline.utils.Resource
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(private val getIssuesByRepo: GetIssuesByRepo) : ViewModel() {

    private val _issuesData = MutableLiveData<List<Issue>>()
    val issuesData: LiveData<List<Issue>>
        get() = _issuesData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    private val compositeDisposable = CompositeDisposable()

    fun getIssues(user: String, repo: String) = compositeDisposable.add(getIssuesByRepo.getIssues(user, repo).subscribe {
        when (it) {
            is Resource.Success -> _issuesData.value = it.data
            is Resource.Error -> {
                _errorData.value = it.message
                _issuesData.value = it.data
            }
            is Resource.Loading -> Unit
        }
    })

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getIssuesByRepo: GetIssuesByRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailViewModel(getIssuesByRepo) as T
    }
}
