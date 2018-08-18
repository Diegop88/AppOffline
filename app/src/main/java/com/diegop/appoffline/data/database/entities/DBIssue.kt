package com.diegop.appoffline.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class DBIssue(
        @PrimaryKey
        val id: Int,
        val user: String,
        val repo: String,
        val title: String,
        val body: String)