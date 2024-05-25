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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
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
        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser=auth.currentUser
        if(currentUser!=null){
            val intent=Intent(this,MainActivity3::class.java)
            intent.putExtra("email",currentUser.email)
            startActivity(intent)
            finish()
        }
        binding.textViewSignUp.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.btnLogIn.setOnClickListener {
        val email=binding.editTextEmail.text.toString()
        val password=binding.editTextPassword.text.toString()
        if(email.equals("") || password.equals("")){
            Toast.makeText(applicationContext,"Email or password cannot be empty",Toast.LENGTH_SHORT).show()
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent=Intent(this,MainActivity3::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception->
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }
}}