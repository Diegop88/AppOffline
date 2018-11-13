package com.diegop.appoffline.data.repository

import android.util.Log
import com.diegop.appoffline.data.database.AppDao
import com.diegop.appoffline.data.database.entities.DBIssue
import com.diegop.appoffline.data.database.entities.DBRepo
import com.diegop.appoffline.data.network.ApiService
import com.diegop.appoffline.data.network.entities.EntityIssue
import com.diegop.appoffline.data.network.entities.EntityRepo
import com.diegop.appoffline.domain.model.Issue
import com.diegop.appoffline.domain.model.Repo
import com.diegop.appoffline.utils.NetworkBoundResourceRx
import com.diegop.appoffline.utils.NetworkHandler
import com.diegop.appoffline.utils.Resource

class AppRepository(private val dao: AppDao, private val api: ApiService, private val networkHandler: NetworkHandler) {

    fun getReposByUser(user: String?) = object : NetworkBoundResourceRx<List<Repo>, List<EntityRepo>>() {

        override fun saveCallResult(data: List<EntityRepo>?) = dao.saveRepos(data?.map { it.toDBRepo() })

        override fun loadFromDb() = dao.getRepos(user).map { it.toRepo() }

        override fun shouldFetch(data: List<Repo>?): Boolean {
            //Revisar y validar datos almacenados
            return true
        }

        override fun createCall() = when (networkHandler.isConnected) {
            true -> api.getRepos(user).execute().let {
                if (it.isSuccessful) {
                    Resource.Success(it.body())
                } else {
                    Resource.Error(it.message(), null)
                }
            }
            false, null -> Resource.Error("No network detected", null)
        }

        override fun onFetchFailed() {
            Log.d("Repositorio", "Mandar error a Crashlytics")
        }

    }.asObservable()

    fun getIssuesByRepo(user: String, repo: String) = object : NetworkBoundResourceRx<List<Issue>, List<EntityIssue>>() {

        override fun saveCallResult(data: List<EntityIssue>?) = dao.saveIssues(data?.map { it.toDBIssue(user, repo) })

        override fun loadFromDb() = dao.getIssues(user, repo).map { it.toIssue() }

        override fun shouldFetch(data: List<Issue>?): Boolean {
            //Revisar y validar datos almacenados
            return true
        }

        override fun createCall() = when (networkHandler.isConnected) {
            true -> api.getIssues(user, repo).execute().run {
                if (isSuccessful) {
                    Resource.Success(body())
                } else {
                    Resource.Error(message(), null)
                }
            }
            false, null -> Resource.Error("No network detected", null)
        }
    }.asObservable()

    fun EntityRepo.toDBRepo() = DBRepo(this.name, this.user.name, this.description)

    fun DBRepo.toRepo() = Repo(this.name, this.user, this.description)

    fun EntityIssue.toDBIssue(user: String, repo: String) = DBIssue(this.id, user, repo, this.title, this.body)

    fun DBIssue.toIssue() = Issue(this.id, this.title, this.body)
}
