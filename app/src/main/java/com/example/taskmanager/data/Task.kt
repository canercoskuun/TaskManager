package com.example.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Task(
    val id:String,
    var title: String,
    var description: String,
    var deadline: String,
    //1 normal 2 önemli 3 çok önemli
    var importanceStatus: Int,
    var status: String,
    var assignedMail: String

): Serializable
