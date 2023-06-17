package com.example.walkbuylogin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class MapDialog : DialogFragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_map, null)
        builder.setView(dialogView)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView2) as SupportMapFragment

        mapFragment.getMapAsync(this)

        // Configura otros elementos del cuadro de diálogo si es necesario

        return builder.create()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Configura opciones o marcadores en el mapa si es necesario
    }
}