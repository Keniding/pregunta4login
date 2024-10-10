package com.example.pregunta4_login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pregunta4_login.models.Personal
import com.example.pregunta4_login.models.PersonalArray
import com.example.pregunta4_login.upload.DownloadImageTask;
import com.google.android.material.bottomnavigation.BottomNavigationView


class WorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_worker)
        //Instancia de la clase Personal Array
        val array= PersonalArray.getInstance();
        if(array.showData().isEmpty()) {
            array.saveRegister(
                Personal(
                    "Juan",
                    "12/05/1992",
                    "Limpieza",
                    "10/01/2024",
                    "Activo",
                    "https://tse1.mm.bing.net/th?id=OIP.AwGBn0RaiFXEpXemdj-2LQHaLG&pid=Api&P=0&h=180",
                    "juanito",
                    "juanito123"
                )
            )
            array.saveRegister(
                Personal(
                    "Maria",
                    "10/01/1990",
                    "Limpieza",
                    "10/01/2023",
                    "Activo",
                    "https://tse4.mm.bing.net/th?id=OIP.D6YaM1jCA84slFtH2M1gnAHaE8&pid=Api&P=0&h=180",
                    "maria",
                    "maria123"
                )
            )
        }
        // Obtén el contenedor para los datos
        val personalContainer: LinearLayout = findViewById(R.id.personal_container)

        val count= intent.getStringExtra("VALUE");
        println("El count es:"+count);

        if(count=="1"){
            val length:Int=array.showData().size;
            val message=array.foundData(length-1,intent.getStringExtra("USER_EXTRA"));
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Registro realizado!!")
            builder.setMessage(message);
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
            intent.putExtra("VALUE","0");
        println("Los datos a mostrar son: "+array.foundData(length-1,intent.getStringExtra("USER_EXTRA")));
        }



        // Infla las vistas para cada elemento de la lista
        for (p in array.showData()) {
            // Usa el layout para cada ítem
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_activity, personalContainer, false)

            val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            val bornTextView:TextView=itemView.findViewById(R.id.ageTextView)
            val roleTextView: TextView = itemView.findViewById(R.id.roleTextView)
            val registrationDateTextView: TextView = itemView.findViewById(R.id.registrationDateTextView)
            val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
            val imageViewProfile: ImageView = itemView.findViewById(R.id.imageViewProfile)

            // Actualiza los datos en las vistas
            nameTextView.text = "${p.name}"
            bornTextView.text="Nacimiento: ${p.born}"
            roleTextView.text = "Rol: ${p.rol}"
            registrationDateTextView.text = "Fecha de Registro: ${p.inputDate}"
            statusTextView.text = "Estado: ${p.status}"

            DownloadImageTask(imageViewProfile).execute(p.photo)

            // Agrega la vista al contenedor
            personalContainer.addView(itemView)
        }
        val register:Button=findViewById(R.id.btn_add_personal)
        register.setOnClickListener {
            val intent = Intent(this, PersonalRegister::class.java).apply {
                putExtra("USER_EXTRA", intent.getStringExtra("USER_EXTRA"))
            }
            startActivity(intent);
        }
        val bottomNavigation: BottomNavigationView =findViewById(R.id.bottom_navigation);
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
    }
}
