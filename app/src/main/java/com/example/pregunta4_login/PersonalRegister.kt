package com.example.pregunta4_login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pregunta4_login.models.Personal
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class PersonalRegister : AppCompatActivity() {
    private val REQUEST_IMAGE_PICK = 1
    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_register)

        val btnUploadPhoto: Button = findViewById(R.id.btn_upload_photo)
        val btnSavePersonal: Button = findViewById(R.id.btn_save_personal)
        val born: CalendarView = findViewById(R.id.calendarView)

        // Check if permission is already granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }

        btnUploadPhoto.setOnClickListener {
            // Intent to pick an image from the gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Configura el listener para el CalendarView fuera del botón
        born.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // La fecha seleccionada
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Imprime la fecha seleccionada en la consola
            println("La fecha seleccionada es: $selectedDate")

            // Solo para mostrar la fecha seleccionada en el botón "Guardar"
            btnSavePersonal.setOnClickListener {
                val name: EditText = findViewById(R.id.et_full_name)
                val rolClean: RadioButton = findViewById(R.id.rb_cleaning)
                val rolSecurity: RadioButton = findViewById(R.id.rb_security)
                val rolAdmin: RadioButton = findViewById(R.id.rb_admin)
                var rol = ""

                /*
                if (rolClean.isChecked) {
                    rol = "Limpieza"
                }
                if (rolSecurity.isChecked) {
                    rol = "Seguridad"
                }
                if (rolAdmin.isChecked) {
                    rol = "Administrador"
                }
                val salary: EditText = findViewById(R.id.et_salary)
                val phone: EditText = findViewById(R.id.et_phone)
                val address: EditText = findViewById(R.id.et_address)
                val photo = "https://tse1.mm.bing.net/th?id=OIP.rU_pV4ssndsrIKvNqgX0KwHaLL&pid=Api&P=0&h=180"
                val namePerson: String = name.text.toString()
                val user:EditText=findViewById(R.id.et_username)
                val password:EditText=findViewById(R.id.et_password)


                if (namePerson.isEmpty() ||selectedDate.isEmpty() ||user.text.isEmpty()||password.text.isEmpty() || rol == "" || salary.text.toString().isEmpty() ||
                    phone.text.toString().isEmpty() || address.text.toString().isEmpty()) {
                    Toast.makeText(this, "Falta completar campos", Toast.LENGTH_SHORT).show()
                } else {
                    if (namePerson.length < 2 || namePerson.length > 100) {
                        Toast.makeText(
                            this,
                            "La longitud del nombre no es valida",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (address.text.toString().length < 5 || address.text.toString().length > 255) {
                            Toast.makeText(
                                this,
                                "La longitud de la dirección no es valida",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (phone.text.toString().length != 9) {
                                Toast.makeText(
                                    this,
                                    "El teléfono debe tener 9 dígitos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val array = PersonalArray.getInstance()
                                array.saveRegister(
                                    Personal(
                                        namePerson,
                                        selectedDate,
                                        rol,
                                        getCurrentDate(),
                                        "Activo",
                                        photo,
                                        user.text.toString(),
                                        password.text.toString()
                                    )
                                )
                                val name=intent.getStringExtra("USER_EXTRA");
                                // Creación del Intent
                                val intent = Intent(this, WorkerActivity::class.java)

                                intent.putExtra("USER_EXTRA",name)


                                intent.putExtra("VALUE", "1")

                                startActivity(intent)

                            }
                        }
                    }
                }

                 */
            }
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
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
