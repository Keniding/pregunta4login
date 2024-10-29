package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.PersonalAdapter
import com.example.pregunta4_login.models.Personal
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.sql.PersonalDatabaseHelper
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

        cargarPersonalDesdeBaseDeDatos()
    }

    private fun cargarPersonal() {
        if (isInternetAvailable()) {
            ApiServiceFactory.cargarPersonalInstance(this).getPersonal().enqueue(object : Callback<List<Personal>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<List<Personal>>, response: Response<List<Personal>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            listaDePersonal.clear()
                            listaDePersonal.addAll(it)
                            adapter.notifyDataSetChanged()

                            val dbHelper = PersonalDatabaseHelper(this@WorkerActivity)
                            for (personal in it) {
                                dbHelper.addPersonal(personal)
                            }
                        }
                    } else {
                        Toast.makeText(this@WorkerActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<Personal>>, t: Throwable) {
                    Toast.makeText(this@WorkerActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "No hay conexión a internet. Cargando datos desde la base de datos.", Toast.LENGTH_LONG).show()
            cargarPersonalDesdeBaseDeDatos()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun cargarPersonalDesdeBaseDeDatos() {
        val dbHelper = PersonalDatabaseHelper(this)
        val listaDesdeBD = dbHelper.getAllPersonal()

        if (listaDesdeBD.isEmpty()) {
            Log.d("WorkerActivity", "No hay datos en la base de datos.")
        } else {
            Log.d("WorkerActivity", "Datos cargados: ${listaDesdeBD.size} registros.")
        }

        listaDePersonal.clear()
        listaDePersonal.addAll(listaDesdeBD)
        adapter.notifyDataSetChanged()

        Log.d(
            "WorkerActivity",
            "Datos cargados desde la base de datos: ${listaDesdeBD.size} registros."
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            networkCapabilities != null && (
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}