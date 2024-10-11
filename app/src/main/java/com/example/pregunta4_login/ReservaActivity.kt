package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.ReservaAdapter
import com.example.pregunta4_login.config.RetrofitClient
import com.example.pregunta4_login.models.Reserva
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class ReservaActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservaAdapter
    private val listaDeReservas = mutableListOf<Reserva>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        recyclerView = findViewById(R.id.recyclerViewReservas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReservaAdapter(listaDeReservas)
        recyclerView.adapter = adapter

        cargarReservas()
    }

    private fun cargarReservas() {
        RetrofitClient.flaskInstance.getReservas().enqueue(object : Callback<List<Reserva>> {
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
    }
}
