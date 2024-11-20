package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
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
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private val apiServiceFactory = ApiServiceFactory
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory(apiServiceFactory) }
    private val meViewModel: MeViewModel by viewModels<MeViewModel> { MeViewModelFactory(application, apiServiceFactory) }

    // UI Components
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var errorMail: TextView
    private lateinit var errorPassword: TextView
    private lateinit var buttonSignIn: Button
    private lateinit var loginGmail: Button
    private lateinit var loginFacebook: Button
    private lateinit var forgotPassword: TextView
    private lateinit var register: TextView

    @SuppressLint("SetTextI18s")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn && token != null) {
            verifyTokenAndRedirect(token)
        } else {
            setupLoginUI()
        }
    }

    private fun verifyTokenAndRedirect(token: String) {
        meViewModel.fetchUserProfile { message, usuario ->
            try {
                val jsonMessage = JSONObject(message)
                val errorMessage = jsonMessage.getString("message")

                if (usuario != null) {
                    navigateToPrincipal()
                } else {
                    if (errorMessage.contains("401") ||
                        errorMessage.contains("autoriza") ||
                        errorMessage.contains("token")
                    ) {
                        clearLoginState()
                        setupLoginUI()
                        showTokenExpiredMessage()
                    } else {
                        Toast.makeText(
                            this,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        clearLoginState()
                        setupLoginUI()
                    }
                }
            } catch (e: JSONException) {
                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()
                clearLoginState()
                setupLoginUI()
            }
        }
    }

    private fun setupLoginUI() {
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initializeViews()
        setupTextWatchers()
        setupClickListeners()
    }

    private fun initializeViews() {
        mail = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPassword)
        errorMail = findViewById(R.id.textViewEmailError)
        errorPassword = findViewById(R.id.textViewPasswordError)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        loginGmail = findViewById(R.id.buttonGmail)
        loginFacebook = findViewById(R.id.buttonFacebook)
        forgotPassword = findViewById(R.id.textViewForgotPassword)
        register = findViewById(R.id.textViewRegister)
    }

    private fun setupTextWatchers() {
        mail.addTextChangedListener {
            validateEmail()
        }

        password.addTextChangedListener {
            validatePassword()
        }
    }

    private fun validateEmail() {
        if (mail.text.toString().isEmpty()) {
            showEmailError()
        } else {
            hideEmailError()
        }
    }

    private fun validatePassword() {
        if (password.text.toString().isEmpty()) {
            showPasswordError()
        } else {
            hidePasswordError()
        }
    }

    private fun showEmailError() {
        errorMail.apply {
            text = "Campo correo electronico requerido"
            setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_red)))
            visibility = View.VISIBLE
        }
    }

    private fun hideEmailError() {
        errorMail.visibility = View.GONE
    }

    private fun showPasswordError() {
        errorPassword.apply {
            text = "Campo contraseña requerida"
            setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_red)))
            visibility = View.VISIBLE
        }
    }

    private fun hidePasswordError() {
        errorPassword.visibility = View.GONE
    }

    private fun setupClickListeners() {
        buttonSignIn.setOnClickListener { handleSignIn() }
        loginGmail.setOnClickListener { showDevelopmentMessage() }
        loginFacebook.setOnClickListener { showDevelopmentMessage() }
        forgotPassword.setOnClickListener { showDevelopmentMessage() }
        register.setOnClickListener { navigateToRegister() }
    }

    private fun handleSignIn() {
        val email = mail.text.toString()
        val pass = password.text.toString()

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        loginViewModel.login(Login(email, pass)) { message, token ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            if (token != null) {
                saveLoginState(token)
                meSave()
                navigateToPrincipal()
            }
        }
    }

    private fun saveLoginState(token: String) {
        saveTokenSecurely(this, token)
        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().apply {
            putBoolean("isLoggedIn", true)
            putString("token", token)
            apply()
        }
        Log.d("LoginActivity", "Token: $token")
    }

    private fun clearLoginState() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply()
    }

    private fun navigateToPrincipal() {
        Intent(this, PrincipalActivity::class.java).also {
            it.putExtra("status", 1)
            startActivity(it)
        }
        finish()
    }

    private fun navigateToRegister() {
        Toast.makeText(this, "Inicio de registro", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun showDevelopmentMessage() {
        Toast.makeText(this, "En desarrollo", Toast.LENGTH_SHORT).show()
    }

    private fun showTokenExpiredMessage() {
        Toast.makeText(
            this,
            "Su sesión ha expirado. Por favor, inicie sesión nuevamente",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun meSave() {
        meViewModel.fetchUserProfile { _, _ -> }
    }
}