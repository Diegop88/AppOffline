package com.diegop.appoffline.data.network

import com.diegop.appoffline.data.network.entities.EntityIssue
import com.diegop.appoffline.data.network.entities.EntityRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{username}/repos")
    fun getRepos(@Path("username") user: String?): Call<List<EntityRepo>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repo: String): Call<List<EntityIssue>>
}
