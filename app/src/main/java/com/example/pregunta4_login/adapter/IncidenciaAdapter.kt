package com.example.pregunta4_login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R
import com.example.pregunta4_login.models.Incidencia
import java.text.SimpleDateFormat
import java.util.Locale

class IncidenciasAdapter : RecyclerView.Adapter<IncidenciasAdapter.IncidenciaViewHolder>() {
    private var incidencias: List<Incidencia> = emptyList()

    fun setIncidencias(nuevasIncidencias: List<Incidencia>) {
        incidencias = nuevasIncidencias
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidenciaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_incidencia, parent, false)
        return IncidenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncidenciaViewHolder, position: Int) {
        val incidencia = incidencias[position]
        holder.bind(incidencia)
    }

    override fun getItemCount() = incidencias.size

    class IncidenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIdIncidencia: TextView = itemView.findViewById(R.id.tvIdIncidencia)
        private val tvIdArea: TextView = itemView.findViewById(R.id.ivArea)
        private val tvNombreArea: TextView = itemView.findViewById(R.id.tvNombreArea)
        private val tvFechaCreacion: TextView = itemView.findViewById(R.id.tvFechaCreacion)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)

        fun bind(incidencia: Incidencia) {
            try {
                // IDs
                tvIdIncidencia.text = incidencia.idIncidencia.toString()
                tvIdArea.text = incidencia.idArea.toString()

                // Área
                val area = incidencia.area.firstOrNull()
                tvNombreArea.text = area?.nombre ?: "Área no especificada"

                // Fecha formateada
                tvFechaCreacion.text = formatearFecha(incidencia.fechaCreacion)

                // Descripción detallada de la incidencia
                val descripcion = incidencia.detalleIncidencia.firstOrNull()?.descripcion
                tvDescripcion.text = descripcion ?: "Sin descripción"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun formatearFecha(fechaStr: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy, HH:mm", Locale("es", "ES"))
                val fecha = inputFormat.parse(fechaStr)
                outputFormat.format(fecha!!)
            } catch (e: Exception) {
                e.printStackTrace()
                fechaStr
            }
        }
    }
}
