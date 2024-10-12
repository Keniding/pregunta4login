package com.example.pregunta4_login.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pregunta4_login.models.Rol

class RolArrayAdapter(context: Context, private val roles: List<Rol>) :
    ArrayAdapter<Rol>(context, android.R.layout.simple_dropdown_item_1line, roles) {

    override fun getItem(position: Int): Rol {
        return roles[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).text = roles[position].nombre
        return view
    }
}
