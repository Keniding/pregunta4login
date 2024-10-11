package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.RolArrayAdapter
import com.example.pregunta4_login.config.RetrofitClient
import com.example.pregunta4_login.models.Rol
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val listaDeRoles = mutableListOf<Rol>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.etRol)
        cargarRoles(autoCompleteTextView)
    }

    private fun cargarRoles(autoCompleteTextView: AutoCompleteTextView) {
        RetrofitClient.laravelInstance.getRoles().enqueue(object : Callback<List<Rol>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaDeRoles.clear()
                        listaDeRoles.addAll(it)
                        val adapter = RolArrayAdapter(this@RegisterActivity, listaDeRoles)
                        autoCompleteTextView.setAdapter(adapter)
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
