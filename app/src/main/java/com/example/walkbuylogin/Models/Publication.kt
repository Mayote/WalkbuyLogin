package com.example.walkbuylogin.Models

data class Publication(
    var id: String = "",
    var id_user: String = "",
    var title: String = "",
    var category: String = "",
    var quantity: Int = 0,
    var description: String = "",
    var photo: String ="",
    var ubication: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
)
