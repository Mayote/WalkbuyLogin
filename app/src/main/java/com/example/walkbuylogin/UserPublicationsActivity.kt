package com.example.walkbuylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.walkbuylogin.Adapter.UserAplicationAdapter
import com.example.walkbuylogin.Models.Publication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserPublicationsActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var btn_publications: Button
    private lateinit var btn_add: FloatingActionButton
    private lateinit var publications: MutableList<Publication>
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_publications)

        listView = findViewById(R.id.UserPublicationsRecyclerView)
        btn_publications = findViewById(R.id.btn_publications)
        btn_add = findViewById(R.id.fb_add_publication)
        listView.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.VERTICAL)
        listView.setHasFixedSize(true)
        publications = mutableListOf()

        btn_publications.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        btn_add.setOnClickListener{
            val intent = Intent(this, CreateProductActivity::class.java)
            startActivity(intent)
        }

        val auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            db.collection("publications").whereEqualTo("id_user", uid).get().addOnSuccessListener {
                for (document in it.documents) {
                    val publication: Publication? = document.toObject(Publication::class.java)
                    if (publication != null) {
                        publications.add(publication)
                    }
                }
                listView.adapter = UserAplicationAdapter(this, publications,db)
            }
        } else {
            // No hay usuario autenticado
        }


    }
}