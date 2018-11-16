package com.diegop.appoffline.data.repository

import com.diegop.appoffline.data.database.AppDao
import com.diegop.appoffline.data.database.entities.DBIssue
import com.diegop.appoffline.data.database.entities.DBRepo
import com.diegop.appoffline.data.network.ApiService
import com.diegop.appoffline.data.network.entities.EntityIssue
import com.diegop.appoffline.data.network.entities.EntityRepo
import com.diegop.appoffline.domain.model.Issue
import com.diegop.appoffline.domain.model.Repo
import com.diegop.appoffline.utils.NetworkBoundResourceRx
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AppRepository(private val dao: AppDao, private val api: ApiService) {

    suspend fun getReposByUser(user: String?) = object : NetworkBoundResourceRx<List<Repo>, List<EntityRepo>>() {
        override suspend fun loadFromDb() = GlobalScope.async { dao.getRepos(user).map { it.toRepo() } }.await()

        override fun shouldFetch(data: List<Repo>?) = true

        override suspend fun createCall() = api.getRepos(user).await()

        override suspend fun saveCallResult(data: List<EntityRepo>?) = GlobalScope.async { dao.saveRepos(data?.map { it.toDBRepo() }) }.await()
    }.invoke()

    suspend fun getIssuesByRepo(user: String, repo: String) = object : NetworkBoundResourceRx<List<Issue>, List<EntityIssue>>() {
        override suspend fun loadFromDb() = GlobalScope.async { dao.getIssues(user, repo).map { it.toIssue() } }.await()

        override fun shouldFetch(data: List<Issue>?) = true

        override suspend fun createCall() = api.getIssues(user, repo).await()

        override suspend fun saveCallResult(data: List<EntityIssue>?) = GlobalScope.async { dao.saveIssues(data?.map { it.toDBIssue(user, repo) }) }.await()
    }.invoke()

    fun EntityRepo.toDBRepo() = DBRepo(this.name, this.user.name, this.description)

    fun DBRepo.toRepo() = Repo(this.name, this.user, this.description)

    fun EntityIssue.toDBIssue(user: String, repo: String) = DBIssue(this.id, user, repo, this.title, this.body)

    fun DBIssue.toIssue() = Issue(this.id, this.title, this.body)
}
