package com.example.taskmanager.view

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.UserDatabase
import com.example.taskmanager.databinding.ActivityMain3Binding
import com.example.taskmanager.databinding.ActivityMain4Binding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings

class MainActivity4 : AppCompatActivity() {
    private lateinit var binding: ActivityMain4Binding
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar2)
        supportActionBar?.title =""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db= Firebase.firestore
        binding.editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.editTextDate.setText("$dayOfMonth/${month + 1}/$year")
            }, currentYear, currentMonth, currentDay)
            datePickerDialog.show()
        }
        val önemDurumları = arrayOf("Normal","Önemli", "Çok Önemli")
        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, önemDurumları)
        val görevDurumları= arrayOf("Yapılacak","Bekleyen","Aktif","Tamamlandı","Tamamlanan")
        binding.spinner2.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, görevDurumları)

        binding.btnCancel.setOnClickListener{
           onBackPressed()
        }
        //room için tanımlama

        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.btnSave.setOnClickListener {
            val title=binding.editTextTitle.text.toString()
            val description=binding.editTextDescription.text.toString()
            val date=binding.editTextDate.text.toString()
            var importance=1
            when (binding.spinner.selectedItem.toString()) {
                "Önemli" -> importance = 2
                "Çok Önemli" -> importance = 3
            }
            val status=binding.spinner2.selectedItem.toString()
            val mail=binding.editTextMail.text.toString()
            val task = hashMapOf(
                "title" to title,
                "description" to description,
                "date" to date,
                "importance" to importance,
                "status" to status,
                "mail" to mail
            )
            db.collection("Task").add(task)
                .addOnSuccessListener { documentReference ->
                    Log.e("Task", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("Task", "Error adding document", e)
                }
            Toast.makeText(this,"Görev eklendi",Toast.LENGTH_LONG).show()
            onBackPressed()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}