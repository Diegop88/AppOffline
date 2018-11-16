package com.diegop.appoffline.domain.usecase.repo

import com.diegop.appoffline.data.repository.AppRepository

class GetReposByUser(private val repo: AppRepository) {
    suspend operator fun invoke(user: String?) = repo.getReposByUser(user)
}
