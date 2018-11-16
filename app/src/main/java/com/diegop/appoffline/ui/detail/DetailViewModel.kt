package com.diegop.appoffline.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Issue
import com.diegop.appoffline.domain.usecase.issue.GetIssuesByRepo
import com.diegop.appoffline.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val getIssuesByRepo: GetIssuesByRepo) : ViewModel() {

    private val _issuesData = MutableLiveData<List<Issue>>()
    val issuesData: LiveData<List<Issue>>
        get() = _issuesData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String>
        get() = _errorData

    fun getIssues(user: String, repo: String) = GlobalScope.launch(Dispatchers.IO) {
        val result = getIssuesByRepo(user, repo)
        withContext(Dispatchers.Main) {
            when (result) {
                is Resource.Success -> _issuesData.value = result.data
                is Resource.Error -> {
                    _errorData.value = result.error.message
                    _issuesData.value = result.data
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getIssuesByRepo: GetIssuesByRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailViewModel(getIssuesByRepo) as T
    }
}
