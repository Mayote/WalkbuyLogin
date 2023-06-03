package com.example.walkbuylogin.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walkbuylogin.Models.Publication
import com.example.walkbuylogin.R
import com.squareup.picasso.Picasso


class PublicationAdapter(var context: Context, var listPublication: MutableList<Publication>): RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>(){

    inner class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var titulo: TextView = itemView.findViewById(R.id.HolderTitle)
        var category: TextView = itemView.findViewById(R.id.HolderCategory)
        var cantidad: TextView = itemView.findViewById(R.id.HolderQuantity)
        var image: ImageView = itemView.findViewById(R.id.HolderImage)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): PublicationAdapter.PublicationViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.view_publications_holder, parent, false)
        return PublicationViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listPublication.size
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
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

        holder.itemView.setOnClickListener(){
            abrirGoogleMaps(publication.lat,publication.lon)
        }


    }

    private fun abrirGoogleMaps(latitud: Double, longitud: Double) {
        val uri = "geo:$latitud,$longitud?q=Estoy Aqui"


        val gmmIntentUri = Uri.parse("google.navigation:q=$latitud,$longitud")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }


}