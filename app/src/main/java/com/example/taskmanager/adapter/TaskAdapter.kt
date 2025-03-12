package com.example.taskmanager.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.UserDatabase
import com.example.taskmanager.databinding.CardTasarimBinding
import com.example.taskmanager.view.MainActivity2
import com.example.taskmanager.view.MainActivity3
import com.example.taskmanager.view.MainActivity5
import com.example.taskmanager.view.MainActivity6
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class TaskAdapter(private val context: Context, val taskList:ArrayList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(val binding: CardTasarimBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=CardTasarimBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val task=taskList.get(position)
        with(holder){
            binding.apply {
              textViewTaskTitle.text=task.title
              textViewTaskDescription.text=task.description
                card.setOnClickListener {
                    val intent: Intent = Intent(context, MainActivity6::class.java)

                    intent.putExtra("task", task)
                    context.startActivity(intent)
                }
                imageViewDelete.setOnClickListener {
                    MaterialAlertDialogBuilder(context)
                        .setBackground(context.getDrawable(R.drawable.edit_text_background))
                        .setTitle("Görevi Sil")
                        .setMessage("Görevi silmek istediğinize emin misiniz? Bu işlem geri alınamaz ve görev kalıcı olarak silinecek. Devam etmek istiyor musunuz?")
                        .setPositiveButton("Evet"){d,i->
                            val db = FirebaseFirestore.getInstance()
                            db.collection("Task")
                                .document(task.id)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context,"Görev silindi",Toast.LENGTH_LONG).show()
                                    val intent: Intent = Intent(context, MainActivity3::class.java)
                                    context.startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context,"Görev silinirken bir hata oluştu: ${e.localizedMessage}",Toast.LENGTH_LONG).show()
                                }
                        }
                        .setNegativeButton("İPTAL"){d,i->
                            // İPTAL EDİLDİ!
                        }
                        .show()
                }



            }
        }


    }

}
