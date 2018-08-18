package com.diegop.appoffline.data.network.entities

import com.google.gson.annotations.SerializedName

class EntityRepo(
        @SerializedName("name")
        val name: String,
        @SerializedName("owner")
        val user: EntityOwner,
        @SerializedName("description")
        val description: String?)

class EntityOwner(
        @SerializedName("login")
        val name: String)