package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pregunta4_login.adapter.AreaArrayAdapter
import com.example.pregunta4_login.models.Area
import com.example.pregunta4_login.models.Mensaje
import com.example.pregunta4_login.models.Reserva
import com.example.pregunta4_login.models.ReservaRequest
import com.example.pregunta4_login.services.ApiServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class DetalleReservaActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_reserva)

        // Obtener los datos del Intent
        val areaId = intent.getIntExtra("id_area", -1)
        val fechaReserva = intent.getStringExtra("fecha_reserva")
        val horaInicio = intent.getStringExtra("hora_inicio")
        val horaFin = intent.getStringExtra("hora_fin")

        // Convertir la fecha a milisegundos
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = fechaReserva?.let { dateFormat.parse(it) }
        val dateInMillis = date?.time ?: System.currentTimeMillis()

        // Configurar el CalendarView
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.setDate(dateInMillis, true, true)

        // Deshabilitar la interacción del usuario con el CalendarView
        calendarView.setOnTouchListener { _, _ -> true }

        // Actualizar los TextView con los datos recibidos
        findViewById<TextView>(R.id.tv_reservation_title).text = "Reserva de Espacio - Área: $areaId"
        findViewById<TextView>(R.id.tv_time_range).text = "Rango de horas: $horaInicio - $horaFin"

        val descripcionEditText = findViewById<EditText>(R.id.et_reservation_reason)
        val descripcionText = descripcionEditText.text.toString()

        val reserva = ReservaRequest(areaId, fechaReserva!!, horaInicio!!, horaFin!!, descripcionText)

        try {
            guardarReserva(reserva)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun guardarReserva(reserva: ReservaRequest) {
        ApiServiceFactory.crearReservaInstance(this)
            .crearReserva(reserva).enqueue(object : Callback<Mensaje> {
                override fun onResponse(call: Call<Mensaje>, response: Response<Mensaje>) {
                    if (response.isSuccessful) {
                        response.body()?.let { message ->
                            if (message.Mensaje == "messages.savedReserva") {
                                Toast.makeText(
                                    this@DetalleReservaActivity,
                                    "Reserva guardada exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@DetalleReservaActivity,
                                    "Respuesta inesperada: ${message.Mensaje}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@DetalleReservaActivity,
                            "Error al guardar la reserva: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Mensaje>, t: Throwable) {
                    Toast.makeText(
                        this@DetalleReservaActivity,
                        "Fallo en la conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}
