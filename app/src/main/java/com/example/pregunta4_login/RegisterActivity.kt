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
    private var rolSeleccionadoActual: Rol? = null

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
            try {
                val rolSeleccionado = listaDeRoles[position]
                rolSeleccionadoActual = rolSeleccionado
                idRolSeleccionado = rolSeleccionado.id

                Log.d("RegisterActivity", "=== Selección de Rol ===")
                Log.d("RegisterActivity", "Rol seleccionado: ${rolSeleccionado.nombre}")
                Log.d("RegisterActivity", "ID del rol: ${rolSeleccionado.id}")
                Log.d("RegisterActivity", "Lista completa de roles: $listaDeRoles")
                Log.d("RegisterActivity", "Posición seleccionada: $position")
                Log.d("RegisterActivity", "=== Fin Selección ===")
            } catch (e: Exception) {
                Log.e("RegisterActivity", "Error al seleccionar rol", e)
                Toast.makeText(this, "Error al seleccionar rol", Toast.LENGTH_SHORT).show()
            }
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
        val rolText = findViewById<AutoCompleteTextView>(R.id.etRol).text.toString()
        val image = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973461_1280.png"

        Log.d("RegisterActivity", "=== Validación de Usuario ===")
        Log.d("RegisterActivity", "Nombre: $name")
        Log.d("RegisterActivity", "Email: $email")
        Log.d("RegisterActivity", "Rol texto: $rolText")
        Log.d("RegisterActivity", "ID Rol: $idRolSeleccionado")
        Log.d("RegisterActivity", "Rol actual: $rolSeleccionadoActual")
        Log.d("RegisterActivity", "=== Fin Validación ===")

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rolText.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            return
        }

        if (idRolSeleccionado == 0) {
            Log.d("RegisterActivity", "=== Error de Rol ===")
            Log.d("RegisterActivity", "Lista de roles disponibles: $listaDeRoles")
            Log.d("RegisterActivity", "Texto del rol ingresado: $rolText")
            Log.d("RegisterActivity", "=== Fin Error ===")

            Toast.makeText(this, "Por favor, seleccione un rol válido", Toast.LENGTH_LONG).show()
            return
        }

        val usuario = Usuario(name, dni, email, password, idRolSeleccionado, image)

        val factory = RegisterViewModelFactory(ApiServiceFactory)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        viewModel.guardarUsuario(usuario) { message ->
            Log.d("RegisterActivity", "Respuesta del servidor: $message")
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (message.contains("Usuario registrado con éxito", ignoreCase = true)) {
                Intent(this, PrincipalActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }
        }
    }

    private fun cargarRoles(autoCompleteTextView: AutoCompleteTextView) {
        ApiServiceFactory.laravelInstance.getRoles().enqueue(object : Callback<List<Rol>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if (response.isSuccessful) {
                    response.body()?.let { roles ->
                        Log.d("RegisterActivity", "=== Carga de Roles ===")
                        Log.d("RegisterActivity", "Roles recibidos: $roles")

                        listaDeRoles.clear()
                        listaDeRoles.addAll(roles)

                        Log.d("RegisterActivity", "Lista después de cargar: $listaDeRoles")
                        Log.d("RegisterActivity", "=== Fin Carga ===")

                        val adapter = RolArrayAdapter(this@RegisterActivity, listaDeRoles)
                        autoCompleteTextView.setAdapter(adapter)
                    }
                } else {
                    Log.e("RegisterActivity", "Error en la respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@RegisterActivity, "Error al obtener datos: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Log.e("RegisterActivity", "Error de conexión", t)
                Toast.makeText(this@RegisterActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
