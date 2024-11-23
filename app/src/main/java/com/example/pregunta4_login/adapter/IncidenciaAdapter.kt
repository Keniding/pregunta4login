package com.example.pregunta4_login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R
import com.example.pregunta4_login.models.Incidencia

class IncidenciasAdapter : RecyclerView.Adapter<IncidenciasAdapter.IncidenciaViewHolder>() {

    private var incidencias: List<Incidencia> = emptyList()

    fun setIncidencias(incidencias: List<Incidencia>) {
        this.incidencias = incidencias
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidenciaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reporte, parent, false)
        return IncidenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncidenciaViewHolder, position: Int) {
        val incidencia = incidencias[position]
        holder.bind(incidencia)
    }

    override fun getItemCount() = incidencias.size

    class IncidenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIncidenciaId: TextView = itemView.findViewById(R.id.tvIncidenciaId)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvArea: TextView = itemView.findViewById(R.id.tvArea)
        private val tvDni: TextView = itemView.findViewById(R.id.tvDni)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)

        fun bind(incidencia: Incidencia) {
            tvIncidenciaId.text = "Incidencia #${incidencia.idIncidencia}"
            tvFecha.text = incidencia.fechaCreacion
            tvArea.text = "Área: ${incidencia.area?.nombre ?: "No especificada"}"
            tvDni.text = "DNI: ${incidencia.dni}"
            tvDescripcion.text = incidencia.detalleIncidencia?.descripcion ?: "Sin descripción"
        }
    }
}
