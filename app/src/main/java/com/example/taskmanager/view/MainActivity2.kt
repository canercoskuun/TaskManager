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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var users:ArrayList<User>
    private lateinit var auth: FirebaseAuth
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
        auth = Firebase.auth
        binding.buttonLogIn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCreateAccount.setOnClickListener {
        val email=binding.editTextEmail.text.toString()
        val password=binding.editTextPassword.text.toString()
        val password2=binding.editTextPassword2.text.toString()
        if(email.equals("") || password.equals("")){
            Toast.makeText(applicationContext,"Email or password cannot be empty",Toast.LENGTH_SHORT).show()
        }
        else {
            if (password2 == password && binding.checkBox.isChecked) {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(applicationContext,"Lütfen tekrar kontrol ediniz!",Toast.LENGTH_SHORT).show()
            }
        }
    }}
}