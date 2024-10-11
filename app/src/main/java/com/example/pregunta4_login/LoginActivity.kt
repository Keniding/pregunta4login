package com.example.pregunta4_login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import com.example.pregunta4_login.models.User
import com.google.android.material.textfield.TextInputEditText



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val mail:TextInputEditText=findViewById(R.id.editTextEmail)
        val password:TextInputEditText=findViewById(R.id.editTextPassword)
        val errorMail:TextView=findViewById(R.id.textViewEmailError)
        val errorPasword:TextView=findViewById(R.id.textViewPasswordError)
        val buttonSignIn:Button=findViewById(R.id.buttonSignIn)
        val loginGmail:Button=findViewById(R.id.buttonGmail)
        val loginFacebook:Button=findViewById(R.id.buttonFacebook)
        val forgotPassword:TextView=findViewById(R.id.textViewForgotPassword)
        val register:TextView=findViewById(R.id.textViewRegister)

        mail.addTextChangedListener{
           if(mail.text.toString().isEmpty()){
               errorMail.text="Campo correo electronico requerido";
               errorMail.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.dark_red)))
               errorMail.visibility=View.VISIBLE
           }
            if(mail.text.toString().isNotEmpty()){
                errorMail.visibility=View.GONE
            }
        }
        password.addTextChangedListener {
            if(password.text.toString().isEmpty()){
                errorPasword.text="Campo contraseña requerida";
                errorPasword.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.dark_red)))
                errorPasword.visibility=View.VISIBLE
            }
            if(password.text.toString().isNotEmpty()){
                errorPasword.visibility=View.GONE
            }
        }

        buttonSignIn.setOnClickListener{
            if(mail.text.toString()=="admin" && password.text.toString()=="admin"){
             val user= User("Daniel","admin","admin","administrador");
                val intent = Intent(this, PrincipalActivity::class.java).apply {
                    putExtra("USER_EXTRA", user.name)
                }
                startActivity(intent)
            }
            else{
              Toast.makeText(this,"Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        }
        loginGmail.setOnClickListener {
            Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
        }
        loginFacebook.setOnClickListener {
            Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
        }
        forgotPassword.setOnClickListener {
            Toast.makeText(this,"En desarrollo",Toast.LENGTH_SHORT).show();
        }
        register.setOnClickListener {
            Toast.makeText(this,"Inicio de registro",Toast.LENGTH_SHORT).show();
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}