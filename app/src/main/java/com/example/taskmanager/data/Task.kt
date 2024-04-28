package com.example.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var taskId: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "deadline")
    var deadline: String,
    //1 normal 2 önemli 3 çok önemli
    @ColumnInfo(name = "importanceStatus")
    var importanceStatus: Int,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "assignedMail")
    var assignedMail: String

): Serializable