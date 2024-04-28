package com.example.taskmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ActivityMain2Binding
import com.example.taskmanager.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var listItems: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = intent.getStringExtra("email")
        binding.textViewEmail.text = email
        listItems = ArrayList()
        listItems.add("Bana atanmış")
        listItems.add("Tüm görevler")
        listItems.add("Bildirimler")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        binding.listView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("email2", binding.textViewEmail.text.toString())
            startActivity(intent)
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            if(selectedItem == "Bana atanmış"){
                val intent: Intent = Intent(this, MainActivity5::class.java)
                intent.putExtra("sayac","1")
                intent.putExtra("email2", binding.textViewEmail.text.toString())
                startActivity(intent)
            }
            else if(selectedItem == "Tüm görevler"){
                val intent: Intent = Intent(this, MainActivity5::class.java)
                intent.putExtra("sayac","2")
                intent.putExtra("email2", binding.textViewEmail.text.toString())
                startActivity(intent)
            }



        }


    }
}