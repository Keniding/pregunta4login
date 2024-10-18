package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.pregunta4_login.models.Login
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.viewmodel.LoginViewModel
import com.example.pregunta4_login.ui.viewmodel.LoginViewModelFactory
import com.example.pregunta4_login.ui.viewmodel.MeViewModel
import com.example.pregunta4_login.ui.viewmodel.MeViewModelFactory
import com.example.pregunta4_login.utils.saveTokenSecurely
import com.google.android.material.textfield.TextInputEditText



class LoginActivity : AppCompatActivity() {

    private val apiServiceFactory = ApiServiceFactory
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory(apiServiceFactory) }
    private val meViewModel: MeViewModel by viewModels<MeViewModel> { MeViewModelFactory(application, apiServiceFactory) }

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

        buttonSignIn.setOnClickListener {
            val email = mail.text.toString()
            val pass = password.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(Login(email, pass)) { message, token ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                if (token != null) {
                    saveTokenSecurely(this, token)
                    meSave();
                    Log.d("LoginActivity", "Token: $token")

                    Intent(this, PrincipalActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
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

    private fun meSave() {
        meViewModel.fetchUserProfile { _, _ -> }
    }
}