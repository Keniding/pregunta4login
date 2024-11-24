package com.example.pregunta4_login.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R
import com.example.pregunta4_login.models.ReciboDetallado
import com.example.pregunta4_login.utils.ReciboDiffCallback
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecibosAdapter : ListAdapter<ReciboDetallado, RecibosAdapter.ReciboViewHolder>(
    ReciboDiffCallback()
) {

    private var onPdfClickListener: ((String) -> Unit)? = null

    fun setOnPdfClickListener(listener: (String) -> Unit) {
        onPdfClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciboViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recibo_detallado, parent, false)
        return ReciboViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ReciboViewHolder, position: Int) {
        holder.bind(getItem(position), onPdfClickListener)
    }

    class ReciboViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIdRecibo: TextView = itemView.findViewById(R.id.tvIdRecibo)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvServicio: TextView = itemView.findViewById(R.id.tvServicio)
        private val tvDni: TextView = itemView.findViewById(R.id.tvDni)
        private val btnVerPdf: MaterialButton = itemView.findViewById(R.id.btnVerPdf)

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(recibo: ReciboDetallado, onPdfClickListener: ((String) -> Unit)?) {
            tvIdRecibo.text = "Recibo #${recibo.idRecibo}"

            val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

            try {
                val fecha = LocalDateTime.parse(recibo.createdAt, formatoEntrada)
                tvFecha.text = fecha.format(formatoSalida)
            } catch (e: Exception) {
                tvFecha.text = recibo.createdAt
            }

            tvServicio.text = "Servicio: ${recibo.servicioNombre}"
            tvDni.text = "DNI: ${recibo.dni}"

            btnVerPdf.setOnClickListener {
                onPdfClickListener?.invoke(recibo.url)
            }
        }
    }
}


