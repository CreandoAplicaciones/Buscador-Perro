package com.pedroPerez.razadeperros

import com.google.gson.annotations.SerializedName

data class DogResponse(@SerializedName("messeger") val imagenes: List<String>, val status: String) {
}