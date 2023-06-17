package com.example.walkbuylogin

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.walkbuylogin.Models.Publication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

class CreateProductActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var category: TextView
    private lateinit var quantity: TextView
    private lateinit var description: TextView

    private lateinit var image: ImageView

    private lateinit var addImage: Button
    private lateinit var deleteImage: Button
    private lateinit var addBtn: Button


    private lateinit var image_uri: Uri
    private lateinit var progressDialog: ProgressDialog
    private lateinit var storageReference: StorageReference
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var Publication: Publication
    private lateinit var uri_image: String

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private lateinit var idd: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        title = findViewById(R.id.CreateTitleInput)
        category = findViewById(R.id.CreateCategoryInput)
        quantity = findViewById(R.id.CreatePriceInput)
        description = findViewById(R.id.CreateDescriptionInput)

        val id = intent.getStringExtra("id")
        image = findViewById(R.id.CreateImageView)

        addImage = findViewById(R.id.CreatePhoto_Btn)
        deleteImage = findViewById(R.id.CreateDelete_Btn)
        addBtn = findViewById(R.id.CreateAdd_btn)

        addImage.setOnClickListener {
            uploadImage()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mAuth = FirebaseAuth.getInstance()
        mfirestore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        if (id == null || id === "") {
            addBtn.setOnClickListener(View.OnClickListener {

                if (title.text.isNotEmpty() && category.text.isNotEmpty() && quantity.text.isNotEmpty() && description.text.isNotEmpty()) {
                    postPublication(title.text.toString(), category.text.toString(), quantity.text.toString().toInt(), description.text.toString())
                } else {
                    Toast.makeText(applicationContext, "Ingresar los datos", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
    fun uploadImage(){
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            image.setImageURI(data?.data)
            image_uri = data?.data!!
            subirPhoto(image_uri)
        }
    }
    private fun subirPhoto(image_url: Uri) {
        var idImage = UUID.randomUUID().toString()
        val rute_storage_photo: String = "images/" + idImage
        val reference: StorageReference = storageReference.child(rute_storage_photo)
        reference.putFile(image_url).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isSuccessful);
            if (uriTask.isSuccessful) {
                uriTask.addOnSuccessListener { uri ->
                    val download_uri = uri.toString()
                    val map = HashMap<String, Any>()
                    uri_image = download_uri
                    map["photo"] = idImage
                    Toast.makeText(this, "Foto actualizada", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Error al cargar foto",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun postPublication(
        title: String,
        category: String,
        quantity: Int,
        description: String
    ) {
        val idUser = mAuth.currentUser!!.uid
        val id = mfirestore.collection("publications").document()

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si el permiso no está otorgado, solicítalo al usuario
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Si el permiso ya está otorgado, obtén la ubicación
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // Obtén la latitud y longitud de la ubicación
                        // Utiliza la ubicación como desees
                        // Aquí puedes abrir Google Maps con la ubicación actual marcada, por ejemplo
                        val map: MutableMap<String, Any> = java.util.HashMap()
                        map["id_user"] = idUser
                        map["id"] = id.id
                        map["title"] = title
                        map["category"] = category
                        map["quantity"] = quantity
                        map["description"] = description
                        map["photo"] = uri_image
                        map["lat"] = location.latitude
                        map["lon"] = location.longitude
                        mfirestore.collection("publications").document(id.id).set(map).addOnSuccessListener {
                            Toast.makeText(applicationContext, "Creado exitosamente", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                applicationContext,
                                "Error al ingresar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

    private fun obtenerUbicacionActual() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }

    }
}