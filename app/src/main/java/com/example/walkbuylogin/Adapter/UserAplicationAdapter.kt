package com.example.walkbuylogin.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walkbuylogin.Models.Publication
import com.example.walkbuylogin.R
import com.google.firebase.firestore.FirebaseFirestore

class UserAplicationAdapter(var context: Context, var listPublication: MutableList<Publication>, var db: FirebaseFirestore): RecyclerView.Adapter<UserAplicationAdapter.UserPublicationViewHolder>() {

    inner class UserPublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var titulo: TextView = itemView.findViewById(R.id.UserPublicationTitle)
        var category: TextView = itemView.findViewById(R.id.UserPublicationCategory)
        var cantidad: TextView = itemView.findViewById(R.id.UserAplicationQuantity)
        var image: ImageView = itemView.findViewById(R.id.UserAplicationPhoto)
        var btn_delete: ImageView = itemView.findViewById(R.id.btn_eliminar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPublicationViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.view_userpublications_holder, parent, false)
        return UserPublicationViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listPublication.size
    }

    override fun onBindViewHolder(holder: UserPublicationViewHolder, position: Int) {
        val publication = listPublication[position]

        holder.titulo.text = publication.title
        holder.category.text = publication.category
        holder.cantidad.text = publication.quantity.toString()
        Log.d("Exception", "${publication.photo}")

        try {
            if (publication.photo != "") Glide
                .with(context)
                .load(publication.photo)
                .into(holder.image)
        } catch (e: Exception) {
            Log.d("Exception", "e: $e")
        }

        holder.btn_delete.setOnClickListener{
            db.collection("publications").document(publication.id).delete().addOnSuccessListener {
                Toast.makeText(context, "Operacion Exitosa", Toast.LENGTH_SHORT,
                ).show()
            }
        }

    }

}