package com.diegop.appoffline.data.network

import com.diegop.appoffline.data.network.entities.EntityIssue
import com.diegop.appoffline.data.network.entities.EntityRepo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{username}/repos")
    fun getRepos(@Path("username") user: String?): Deferred<Response<List<EntityRepo>>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repo: String): Deferred<Response<List<EntityIssue>>>
}
