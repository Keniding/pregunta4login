package com.example.pregunta4_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.adapter.AreaArrayAdapter
import com.example.pregunta4_login.adapter.HorariosAdapter
import com.example.pregunta4_login.models.Area
import com.example.pregunta4_login.models.HorarioReservaRequest
import com.example.pregunta4_login.services.ApiServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearReservaActivity : AppCompatActivity() {

    private lateinit var horariosAdapter: HorariosAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_crear_reserva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerAreas = findViewById<Spinner>(R.id.spinner_areas)
        val areaArrayAdapter = AreaArrayAdapter(this, emptyList())
        spinnerAreas.adapter = areaArrayAdapter

        var areaId: Int = 1

        spinnerAreas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedArea = parent.getItemAtPosition(position) as Area
                areaId = selectedArea.id
                Toast.makeText(
                    this@CrearReservaActivity,
                    "Área seleccionada: ${selectedArea.nombre}, ID: $areaId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            Toast.makeText(this, "Fecha seleccionada: $selectedDate", Toast.LENGTH_SHORT).show()

            cargarHorarios(selectedDate, areaId)
        }

        recyclerView = findViewById(R.id.recycler_view_horarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarAreas()
    }

    private fun cargarAreas() {
        ApiServiceFactory.cargarAreasInstance(this)
            .getAreas().enqueue(object : Callback<List<Area>> {
                override fun onResponse(call: Call<List<Area>>, response: Response<List<Area>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { areas ->
                            Log.d("Areas", areas.toString())

                            val areaArrayAdapter =
                                AreaArrayAdapter(this@CrearReservaActivity, areas)

                            val spinnerAreas = findViewById<Spinner>(R.id.spinner_areas)
                            spinnerAreas.adapter = areaArrayAdapter
                        }
                    } else {
                        Toast.makeText(
                            this@CrearReservaActivity,
                            "Error al obtener datos: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Area>>, t: Throwable) {
                    Toast.makeText(
                        this@CrearReservaActivity,
                        "Fallo en la conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun cargarHorarios(selectedDate: String, area: Int) {
        ApiServiceFactory.cargarHorariosInstance(this)
            .getReservasDisponibles(HorarioReservaRequest(area, selectedDate)).enqueue(object :
                Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { horarios ->
                            horariosAdapter = HorariosAdapter(horarios) { selectedHorario ->
                                val partes = selectedHorario.split("-")
                                val horaIni = partes[0]
                                val horaFin = partes[1]

                                intent.putExtra("id_area", area)
                                intent.putExtra("fecha_reserva", selectedDate)
                                intent.putExtra("hora_inicio", horaIni)
                                intent.putExtra("hora_fin", horaFin)
                                startActivity(intent.setClass(this@CrearReservaActivity, DetalleReservaActivity::class.java))


                                Toast.makeText(
                                    this@CrearReservaActivity,
                                    "Horario seleccionado: $selectedHorario\nInicio: $horaIni, Fin: $horaFin",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            recyclerView.adapter = horariosAdapter
                        }
                    } else {
                        Toast.makeText(
                            this@CrearReservaActivity,
                            "Error al obtener datos: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Toast.makeText(
                        this@CrearReservaActivity,
                        "Fallo en la conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}