package com.example.taskmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskmanager.R
import com.example.taskmanager.data.User
import com.example.taskmanager.data.UserDatabase
import com.example.taskmanager.databinding.ActivityMain2Binding
class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var users:ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonLogIn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.buttonCreateAccount.setOnClickListener {
            if(binding.editTextPassword.text.toString()==binding.editTextPassword2.text.toString() && binding.checkBox.isChecked()){
                val email:String=binding.editTextEmail.text.toString()
                val password:String=binding.editTextPassword.text.toString()
                val user= User(0,email,password)
                userDatabase?.userDAO()?.insert(user)
                Toast.makeText(this,"Kullanıcı kaydedildi",Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
            else{
            Toast.makeText(this,"Lütfen bilgileri dogru girdiginizden emin olunuz",Toast.LENGTH_LONG).show()
        }
    }}
}