package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.ReservaAdapter
import com.example.pregunta4_login.models.Reserva
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.viewmodel.MeViewModel
import com.example.pregunta4_login.ui.viewmodel.MeViewModelFactory
import com.example.pregunta4_login.ui.viewmodel.ReservaViewModel
import com.example.pregunta4_login.ui.viewmodel.ReservaViewModelFactory

class ReservaActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservaAdapter
    private val listaDeReservas = mutableListOf<Reserva>()

    private val apiServiceFactory = ApiServiceFactory
    private val reservaViewModel: ReservaViewModel by viewModels<ReservaViewModel> { ReservaViewModelFactory(application, apiServiceFactory) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        recyclerView = findViewById(R.id.recyclerViewReservas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReservaAdapter(listaDeReservas)
        recyclerView.adapter = adapter

        val btnCrearReserva = findViewById<Button>(R.id.btnCrearReserva)
        btnCrearReserva.setOnClickListener {
            val intent = Intent(this, CrearReservaActivity::class.java)
            startActivity(intent)
        }

        cargarReservas()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun cargarReservas() {
        /*
        ApiServiceFactory.flaskInstance.getReservas().enqueue(object : Callback<List<Reserva>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Reserva>>, response: Response<List<Reserva>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaDeReservas.clear()
                        listaDeReservas.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@ReservaActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Reserva>>, t: Throwable) {
                Toast.makeText(this@ReservaActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
         */

        reservaViewModel.fetchReservas { message, reservas ->
            if (reservas != null) {
                listaDeReservas.clear()
                listaDeReservas.addAll(reservas)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
