package com.example.pregunta4_login.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        @SuppressLint("SetTextI18n")
        fun bind(personal: Personal) {
            nameTextView.text = "Nombre: ${personal.nombre}"
            roleTextView.text = "Rol: ${personal.idRol}"
            ageTextView.text = "Email: ${personal.email}"
            registrationDateTextView.text = "Fecha de Registro: ${personal.fecha_creacion}"
            statusTextView.text = "Estado: ${if (personal.estado == 1) "Activo" else "Inactivo"}"

            Picasso.get().load(personal.foto).into(imageViewProfile)

//            Glide.with(itemView.context)
//                .load(personal.foto)
//                .centerCrop()
//                .into(imageViewProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personal_card_view, parent, false)
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