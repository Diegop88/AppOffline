package com.diegop.appoffline.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DBRepo(
        @PrimaryKey
        val name: String,
        val user: String,
        val description: String?)