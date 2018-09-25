package com.diegop.appoffline.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.diegop.appoffline.data.database.entities.DBIssue
import com.diegop.appoffline.data.database.entities.DBRepo

@Dao
interface AppDao {

    @Query("SELECT * FROM DBIssue WHERE user LIKE :user AND repo LIKE :repo")
    fun getIssues(user: String?, repo: String?): List<DBIssue>

    @Insert(onConflict = REPLACE)
    fun saveIssues(issues: List<DBIssue>?)

    @Query("SELECT * FROM DBRepo WHERE user LIKE :user")
    fun getRepos(user: String?): List<DBRepo>

    @Insert(onConflict = REPLACE)
    fun saveRepos(resultType: List<DBRepo>?)
}
