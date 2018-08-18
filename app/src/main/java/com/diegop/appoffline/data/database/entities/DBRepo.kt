package com.diegop.appoffline.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class DBRepo(
        @PrimaryKey
        val name: String,
        val user: String,
        val description: String?)