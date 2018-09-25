package com.diegop.appoffline.data.network.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class EntityIssue(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String)
