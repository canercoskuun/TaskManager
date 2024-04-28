package com.example.taskmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.adapter.TaskAdapter
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.UserDatabase
import com.example.taskmanager.databinding.ActivityMain4Binding
import com.example.taskmanager.databinding.ActivityMain5Binding

class MainActivity5 : AppCompatActivity() {
    private lateinit var binding: ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar4)
        supportActionBar?.title =""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val email=intent.getStringExtra("email2")
        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        var taskList = ArrayList<Task>()
        taskList=userDatabase?.taskDAO()?.getAllTasks() as ArrayList<Task>
        val taskAdapter=TaskAdapter(this,taskList,email!!)
        binding.RecyclerView.adapter=taskAdapter
        val sayac = intent.getStringExtra("sayac")
        if(sayac=="1"){

            taskList=userDatabase?.taskDAO()?.getMyTasks(email!!) as ArrayList<Task>
            val taskAdapter=TaskAdapter(this,taskList,email!!)
            binding.RecyclerView.adapter=taskAdapter
        }
        else if(sayac=="2"){
            //default
        }








    }
    override fun onBackPressed() {
        super.onBackPressed()
        val email=intent.getStringExtra("email2")
        val intent: Intent = Intent(this, MainActivity3::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}