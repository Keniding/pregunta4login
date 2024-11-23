package com.example.pregunta4_login.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R
import com.example.pregunta4_login.models.Reserva

class ReservaAdapter(private val listaReservas: List<Reserva>) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reserva_card_view, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = listaReservas[position]
        holder.bind(reserva)
    }

    override fun getItemCount() = listaReservas.size

    class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewEstado: TextView = itemView.findViewById(R.id.chipEstado)
        private val textViewFechaReserva: TextView = itemView.findViewById(R.id.textViewFechaReserva)
        private val textViewHoraInicio: TextView = itemView.findViewById(R.id.textViewHoraInicio)
        private val textViewHoraFin: TextView = itemView.findViewById(R.id.textViewHoraFin)
        private val textViewFechaCreacion: TextView = itemView.findViewById(R.id.textViewFechaCreacion)

        @SuppressLint("SetTextI18n")
        fun bind(reserva: Reserva) {
            textViewEstado.text = "Estado: ${reserva.estado}"
            textViewFechaReserva.text = "Fecha de Reserva: ${reserva.fechaReserva}"
            textViewHoraInicio.text = "Hora Inicio: ${reserva.horaInicio}"
            textViewHoraFin.text = "Hora Fin: ${reserva.horaFin}"
            textViewFechaCreacion.text = "Fecha Creación: ${reserva.fechaCreacion}"
        }
    }
}
