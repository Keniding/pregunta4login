package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrincipalActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)

        val welcome: TextView = findViewById(R.id.tv_welcome_message)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("nombre", "Usuario")

        welcome.text = "Bienvenido $userName:"

        val personal: CardView = findViewById(R.id.cardPersonal)
        val reservas: CardView = findViewById(R.id.cardReservas)
        val recibos: CardView = findViewById(R.id.cardRecibos)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val logout: Button = findViewById(R.id.btn_logout)

        personal.setOnClickListener {
            val intent = Intent(this, WorkerActivity::class.java).apply {
                putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
            }
            startActivity(intent)
        }

        reservas.setOnClickListener {
            val intent = Intent(this, ReservaActivity::class.java).apply {
                putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
            }
            startActivity(intent)
        }

        recibos.setOnClickListener {
            val intent = Intent(this, ReciboActivity::class.java).apply {
                putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
            }
            startActivity(intent)
        }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, PrincipalActivity::class.java).apply {
                        putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
                    }
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PerfilActivity::class.java).apply {
                        putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
                    }
                    startActivity(intent)
                    true
                }
                R.id.nav_notifications -> {
                    Toast.makeText(this, "En desarrollo", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
