package com.pedroPerez.razadeperros

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedroPerez.razadeperros.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*

private lateinit var binding: ActivityMainBinding
private lateinit var adapter: DogAdapter
private val dogImages = mutableListOf<String>()

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchView.setOnQueryTextListener(this)

        initRecycleView()
    }

    private fun initRecycleView() {
        adapter = DogAdapter(dogImages)
        binding.recycleviewDog.layoutManager = LinearLayoutManager(this)
        binding.recycleviewDog.adapter = adapter

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDogByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val images: List<String> = puppies?.imagenes ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()


                } else {
                    showError()
                }
                hideKeyboard()
            }

        }

    }

    private fun showError() {
        Toast.makeText(applicationContext, "Esta raza no existe", Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if (!query.isNullOrEmpty()){
               searchByName(query.lowercase(Locale.getDefault())) }
        return true
       }



    override fun onQueryTextChange(newText: String?): Boolean {
      return true
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
}