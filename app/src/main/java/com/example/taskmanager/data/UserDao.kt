package com.example.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)
    @Delete
    fun delete(user: User)
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun loginUser(email: String, password: String): Boolean
    @Query("SELECT*FROM users")
    fun getAllUsers(): List<User>


}