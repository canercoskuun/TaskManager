package com.example.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task)
    @Delete
    fun delete(task: Task)
    @Update
    fun update(task: Task)
    @Query("SELECT*FROM tasks ORDER BY importanceStatus DESC")
    fun getAllTasks(): List<Task>
    @Query("SELECT * FROM tasks WHERE assignedMail = :email ORDER BY importanceStatus DESC")
    fun getMyTasks(email: String): List<Task>

}