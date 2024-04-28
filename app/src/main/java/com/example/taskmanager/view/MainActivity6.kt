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
import com.example.taskmanager.databinding.ActivityMain6Binding

class MainActivity6 : AppCompatActivity() {
    private lateinit var binding: ActivityMain6Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain6Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar3)
        supportActionBar?.title =""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.editTextDate1.setOnClickListener {
            // Şuanki tarihi al
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            // DatePickerDialog oluştur ve göster
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // Seçilen tarihi EditText içine yaz
                binding.editTextDate1.setText("$dayOfMonth/${month + 1}/$year")
            }, currentYear, currentMonth, currentDay)

            // DatePickerDialog'ı göster
            datePickerDialog.show()
        }
        //spinner ayarlama
        val önemDurumları = arrayOf("Normal","Önemli", "Çok Önemli")
        binding.spinner7.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, önemDurumları)
        //spinner end
        val görevDurumları= arrayOf("Yapılacak","Bekleyen","Aktif","Tamamlandı","Tamamlanan")
        binding.spinner3.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, görevDurumları)
        binding.btnCancel1.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }

        val task = intent.getSerializableExtra("task") as Task
        val id=task.taskId
        binding.editTextTitle1.setText(task.title)
        binding.editTextDescrip.setText(task.description)
        binding.editTextDate1.setText(task.deadline)
        when (task.importanceStatus) {
            1 ->  binding.spinner7.setSelection(0)
            2->     binding.spinner7.setSelection(1)
            3 -> binding.spinner7.setSelection(2)
        }
        when(task.status) {
            "Yapılacak" ->  binding.spinner3.setSelection(0)
            "Bekleyen"->     binding.spinner3.setSelection(1)
            "Aktif" -> binding.spinner3.setSelection(2)
            "Tamamlandı" -> binding.spinner3.setSelection(3)
            "Tamamlanan" -> binding.spinner3.setSelection(4)
        }
        binding.editTextMail1.setText(task.assignedMail)
        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.btnUpdate.setOnClickListener {

            val title=binding.editTextTitle1.text.toString()
            val description=binding.editTextDescrip.text.toString()
            val date=binding.editTextDate1.text.toString()
            var importance=1
            when (binding.spinner7.selectedItem.toString()) {
                "Önemli" -> importance = 2
                "Çok Önemli" -> importance = 3
            }
            val status=binding.spinner3.selectedItem.toString()
            val mail=binding.editTextMail1.text.toString()
            val task2= Task(id,title,description,date,importance,status,mail)
            userDatabase?.taskDAO()?.update(task2)
            val email = intent.getStringExtra("email")
            Toast.makeText(this,"Görev güncellendi",Toast.LENGTH_LONG).show()
            val intent: Intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("email",email)
            startActivity(intent)
            finish()
            sendLocalNotificationToUser(this,mail, title)

        }






    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun sendLocalNotificationToUser(context: Context, userEmail: String, title: String) {
        // Bildirim kanalını oluştur
        createNotificationChannel(context)

        // Bildirim oluştur
        val notificationBuilder = NotificationCompat.Builder(context, "channelId")
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Task Manager")
            .setContentText(title + " adındaki görev güncellendi. ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Bildirimi gönder
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(userEmail.hashCode(), notificationBuilder.build())
    }

    // Bildirim kanalını oluştur
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyApp Channel"
            val descriptionText = "MyApp Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channelId", name, importance).apply {
                description = descriptionText
            }

            // Bildirim kanalını sistemde oluştur
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}