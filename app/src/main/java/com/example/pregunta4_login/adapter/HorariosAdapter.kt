package com.example.pregunta4_login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R

class HorariosAdapter(
    private val horarios: List<String>,
    private val onHorarioSelected: (String) -> Unit
) : RecyclerView.Adapter<HorariosAdapter.HorarioViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    class HorarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewHorario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horario, parent, false)
        return HorarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {
        holder.textView.text = horarios[position]
        holder.textView.isSelected = selectedPosition == position

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onHorarioSelected(horarios[selectedPosition])
        }
    }

    override fun getItemCount(): Int {
        return horarios.size
    }
}
