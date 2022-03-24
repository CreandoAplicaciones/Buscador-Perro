package com.pedroPerez.razadeperros

import com.google.gson.annotations.SerializedName

data class DogResponse(@SerializedName("messege") val imagenes: List<String>, val status: String) {
}
