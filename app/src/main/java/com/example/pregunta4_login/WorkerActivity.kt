package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.PersonalAdapter
import com.example.pregunta4_login.adapter.ReservaAdapter
import com.example.pregunta4_login.models.Personal
import com.example.pregunta4_login.services.ApiServiceFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WorkerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonalAdapter
    private val listaDePersonal = mutableListOf<Personal>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_worker)

        val bottomNavigation: BottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción cuando se selecciona "Home"
                    val intent = Intent(this, PrincipalActivity::class.java).apply {
                        putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
                    }
                    startActivity(intent)
                    true
                }
                // Otras acciones para otros ítems
                R.id.nav_profile -> {
                    Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
                    true
                }
                R.id.nav_notifications->{
                    Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
                    true
                }
                else->false
            }
        }

        recyclerView = findViewById(R.id.recyclerViewPersonal)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PersonalAdapter(listaDePersonal)
        recyclerView.adapter = adapter

        cargarPersonal()
    }

    private fun cargarPersonal() {
        ApiServiceFactory.cargarPersonalInstance(this).getPersonal().enqueue(object : Callback<List<Personal>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Personal>>, response: Response<List<Personal>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaDePersonal.clear()
                        listaDePersonal.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@WorkerActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Personal>>, t: Throwable) {
                Toast.makeText(this@WorkerActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}