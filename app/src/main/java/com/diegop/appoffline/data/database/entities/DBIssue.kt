package com.diegop.appoffline.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DBIssue(
        @PrimaryKey
        val id: Int,
        val user: String,
        val repo: String,
        val title: String,
        val body: String)