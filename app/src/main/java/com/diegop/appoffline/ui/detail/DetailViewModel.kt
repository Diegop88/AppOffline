package com.diegop.appoffline.ui.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.diegop.appoffline.domain.model.Issue
import com.diegop.appoffline.domain.usecase.issue.GetIssuesByRepo
import com.diegop.appoffline.utils.Status

class DetailViewModel(private val getIssuesByRepo: GetIssuesByRepo) : ViewModel() {

    val issuesData = MutableLiveData<List<Issue>>()
    val errorData = MutableLiveData<String>()

    fun getIssues(user: String, repo: String) {
        getIssuesByRepo.getIssues(user, repo).subscribe {
            when (it.status) {
                Status.SUCCESS -> issuesData.value = it.data
                Status.ERROR -> {
                    errorData.value = it.message
                    issuesData.value = it.data
                }
                Status.LOADING -> { }
            }
        }
    }

    class Factory(private val getIssuesByRepo: GetIssuesByRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailViewModel(getIssuesByRepo) as T
    }
}
