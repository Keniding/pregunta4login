package com.example.pregunta4_login

import android.content.ClipData.Item
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.pregunta4_login.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        val welcome:TextView=findViewById(R.id.tv_welcome_message)
        val user=intent.getStringExtra("USER_EXTRA");
        welcome.text="Bienvenido ${user}:"
        val personal: CardView =findViewById(R.id.cardPersonal)
        val bottomNavigation:BottomNavigationView=findViewById(R.id.bottom_navigation);
        val logout:Button=findViewById(R.id.btn_logout)
    personal.setOnClickListener{
        val intent = Intent(this, WorkerActivity::class.java).apply {
            putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
        }
        startActivity(intent)
    }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción cuando se selecciona "Home"
                    val intent = Intent(this, PrincipalActivity::class.java).apply {
                        putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
                    }
                    startActivity(intent)
                    true
                }
                // Otras acciones para otros ítems
                R.id.nav_profile -> {
                    Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
                    true
                }
                R.id.nav_notifications->{
                    Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
                    true
                }
                else->false
            }
        }
        logout.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java));
        }


    }
}