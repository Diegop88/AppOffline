package com.diegop.appoffline.domain.usecase.issue

import com.diegop.appoffline.data.repository.AppRepository

class GetIssuesByRepo(private val repo: AppRepository) {
    suspend operator fun invoke(user: String, repository: String) = repo.getIssuesByRepo(user, repository)
}
