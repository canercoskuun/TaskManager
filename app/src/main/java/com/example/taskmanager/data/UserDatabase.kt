package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 3)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDAO(): UserDao

    companion object {
        private var instance: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            if (instance == null) {
                instance =  Room.databaseBuilder(context,UserDatabase::class.java,"users.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as UserDatabase
        }
    }
}