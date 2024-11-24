package com.example.pregunta4_login.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pregunta4_login.R
import com.example.pregunta4_login.models.Personal
import com.squareup.picasso.Picasso

class PersonalAdapter(private val listaPersonal: List<Personal>) : RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>() {

    class PersonalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProfile: ImageView = itemView.findViewById(R.id.imageViewProfile)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val roleTextView: TextView = itemView.findViewById(R.id.roleTextView)
        val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)
        val registrationDateTextView: TextView = itemView.findViewById(R.id.registrationDateTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusChip)

        @SuppressLint("SetTextI18n")
        fun bind(personal: Personal) {
            nameTextView.text = "Nombre: ${personal.nombre}"
            roleTextView.text = "Rol: ${
                when(personal.idRol) {
                    1 -> "Administrador"
                    2 -> "Propietario"
                    3 -> "Personal de limpieza"
                    4 -> "Seguridad"
                    else -> "Rol no definido"
                }
            }"
            ageTextView.text = "Email: ${personal.email}"
            registrationDateTextView.text = "Fecha de Registro: ${personal.fecha_creacion}"
            statusTextView.text = "Estado: ${if (personal.estado == 1) "Activo" else "Inactivo"}"

            if (personal.foto?.isNotEmpty() == true) {
                Picasso.get().load(personal.foto).into(imageViewProfile)
            } else {
                imageViewProfile.setImageResource(R.drawable.ic_profile)
            }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_personal, parent, false)
        return PersonalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaPersonal.size
    }

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val personal = listaPersonal[position]
        holder.bind(personal)
    }
}