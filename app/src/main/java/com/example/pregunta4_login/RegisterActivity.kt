package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pregunta4_login.adapter.RolArrayAdapter
import com.example.pregunta4_login.models.Rol
import com.example.pregunta4_login.models.Usuario
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.viewmodel.RegisterViewModel
import com.example.pregunta4_login.ui.viewmodel.RegisterViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val listaDeRoles = mutableListOf<Rol>()
    private var idRolSeleccionado: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.etRol)
        cargarRoles(autoCompleteTextView)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            idRolSeleccionado = listaDeRoles[position].idRol
        }

        val buttonRegister = findViewById<Button>(R.id.btnRegister)
        buttonRegister.setOnClickListener {
            val etContrasena = findViewById<EditText>(R.id.etContrasena)
            val password = etContrasena.text.toString()

            if (password.length < 8) {
                Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_LONG).show()
            } else {
                etContrasena.error = null
                guardarUsuario()
            }
        }
    }

    private fun guardarUsuario() {
        val name = findViewById<EditText>(R.id.etNombre).text.toString()
        val dni = findViewById<EditText>(R.id.etDNI).text.toString()
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etContrasena).text.toString()
        val rol = findViewById<AutoCompleteTextView>(R.id.etRol).text.toString()

        Log.d("RegisterActivity", "Rol: $rol, idRol: $idRolSeleccionado")

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rol.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
        } else {
            val usuario = Usuario(name, dni, email, password, idRolSeleccionado)
            Log.d("RegisterActivity", "Usuario: $usuario")

            val factory = RegisterViewModelFactory(ApiServiceFactory)
            val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

            viewModel.guardarUsuario(usuario) { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                if (message.contains("Usuario registrado con éxito", ignoreCase = true)) {
                    Intent(this, PrincipalActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
            }
        }
    }

    private fun cargarRoles(autoCompleteTextView: AutoCompleteTextView) {
        ApiServiceFactory.laravelInstance.getRoles().enqueue(object : Callback<List<Rol>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaDeRoles.clear()
                        listaDeRoles.addAll(it)
                        val adapter = RolArrayAdapter(this@RegisterActivity, listaDeRoles)
                        autoCompleteTextView.setAdapter(adapter)
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
