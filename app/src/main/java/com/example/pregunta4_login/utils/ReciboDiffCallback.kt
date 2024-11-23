package com.example.pregunta4_login.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.pregunta4_login.models.ReciboDetallado

class ReciboDiffCallback : DiffUtil.ItemCallback<ReciboDetallado>() {
    override fun areItemsTheSame(oldItem: ReciboDetallado, newItem: ReciboDetallado): Boolean {
        return oldItem.idRecibo == newItem.idRecibo
    }

    override fun areContentsTheSame(oldItem: ReciboDetallado, newItem: ReciboDetallado): Boolean {
        return oldItem == newItem
    }
}