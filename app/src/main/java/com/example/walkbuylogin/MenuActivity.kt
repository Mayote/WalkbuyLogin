package com.example.walkbuylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.walkbuylogin.Adapter.PublicationAdapter
import com.example.walkbuylogin.Models.Publication
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private lateinit var addBtn: Button

    private lateinit var listView: RecyclerView
    private lateinit var publications: MutableList<Publication>
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        addBtn = findViewById(R.id.MenuAdd_btn)

        listView = findViewById(R.id.recyclerViewSingle)

        addBtn.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java)
            startActivity(intent)
        }

        listView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        listView.setHasFixedSize(true)
        publications = mutableListOf()

        db = FirebaseFirestore.getInstance()


        db.collection("publications").get().addOnSuccessListener {
            for (document in it.documents) {
                val publication: Publication? = document.toObject(Publication::class.java)
                if (publication != null) {
                    publications.add(publication)
                }
            }
            listView.adapter = PublicationAdapter(this,publications)
        }
    }
}