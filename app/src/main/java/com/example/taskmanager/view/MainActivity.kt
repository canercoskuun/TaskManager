package com.example.taskmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskmanager.R
import com.example.taskmanager.data.UserDatabase
import com.example.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userDatabase= UserDatabase.getDatabase(applicationContext)
        binding.btnLogIn.setOnClickListener {
            val isLoginSuccesfull=userDatabase.userDAO().loginUser(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString())
            if(isLoginSuccesfull){
                val intent: Intent = Intent(this, MainActivity3::class.java)
                intent.putExtra("email", binding.editTextEmail.text.toString())
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Email adresi veya şifre hatalı!",Toast.LENGTH_LONG).show()
            }

        }
        binding.textViewSignUp.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }




}