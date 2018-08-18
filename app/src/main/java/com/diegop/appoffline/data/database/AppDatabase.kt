package com.diegop.appoffline.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.diegop.appoffline.data.database.entities.DBIssue
import com.diegop.appoffline.data.database.entities.DBRepo

@Database(entities = [DBIssue::class, DBRepo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issuesDao(): AppDao
}
