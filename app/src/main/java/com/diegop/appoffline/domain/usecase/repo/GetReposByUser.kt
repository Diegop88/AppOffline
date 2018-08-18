package com.diegop.appoffline.domain.usecase.repo

import com.diegop.appoffline.data.repository.AppRepository

class GetReposByUser(private val repo: AppRepository) {
    fun getReposByUser(user: String?) = repo.getReposByUser(user)
}
