package com.example.taskmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MainActivity5 : AppCompatActivity() {
    private lateinit var binding: ActivityMain5Binding
    private lateinit var db :FirebaseFirestore
    private lateinit var taskList:ArrayList<Task>
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var auth: FirebaseAuth
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
        auth = Firebase.auth
        db= Firebase.firestore
        val email=auth.currentUser?.email.toString()
        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        val sayac = intent.getStringExtra("sayac")
        if(sayac=="1"){
        getAllData2()
        }
        else if(sayac=="2"){
            getAllData()
        }
    }
    private fun getAllData() {
        taskList = ArrayList<Task>()
        db.collection("Task").orderBy("importance", Query.Direction.DESCENDING)
            .get().addOnSuccessListener { documents ->
            for (document in documents) {
               val id=document.id
                val title = document.getString("title")
                val description = document.getString("description")
                val date = document.getString("date")
                val importance = document.getLong("importance")?.toInt() ?: 0
                val status = document.getString("status")
                val mail = document.getString("mail")
                if (title != null && description != null && date != null && status != null && mail != null) {
                    val task = Task(id,title, description, date, importance, status, mail)
                    taskList.add(task)
                    Log.e("CANER", task.title)
                }
            }
            val email=intent.getStringExtra("email2")
            taskAdapter = TaskAdapter(this, taskList)
            binding.RecyclerView.adapter = taskAdapter
        }.addOnFailureListener { exception ->

            Toast.makeText(this, "Error getting documents: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()

        }
    }
    private fun getAllData2() {

        taskList = ArrayList<Task>()
        db.collection("Task").whereEqualTo("mail", auth.currentUser!!.email.toString())
            .orderBy("importance", Query.Direction.DESCENDING)  // Verileri önem sırasına göre sırala
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id=document.id
                    val title = document.getString("title")
                    val description = document.getString("description")
                    val date = document.getString("date")
                    val importance = document.getLong("importance")?.toInt() ?: 0
                    val status = document.getString("status")
                    val mail = document.getString("mail")
                    if (title != null && description != null && date != null && status != null && mail != null) {
                        val task = Task(id,title, description, date, importance, status, mail)
                        taskList.add(task)
                        Log.e("CANER", task.title)
                    }
                }
                taskAdapter = TaskAdapter(this, taskList)
                binding.RecyclerView.adapter = taskAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
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