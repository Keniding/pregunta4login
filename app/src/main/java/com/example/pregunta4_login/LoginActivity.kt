package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.lifecycle.lifecycleScope
import com.example.pregunta4_login.models.Login
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.ui.viewmodel.LoginViewModel
import com.example.pregunta4_login.ui.viewmodel.LoginViewModelFactory
import com.example.pregunta4_login.ui.viewmodel.MeViewModel
import com.example.pregunta4_login.ui.viewmodel.MeViewModelFactory
import com.example.pregunta4_login.utils.saveTokenSecurely
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private val apiServiceFactory = ApiServiceFactory
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory(apiServiceFactory) }
    private val meViewModel: MeViewModel by viewModels { MeViewModelFactory(application, apiServiceFactory) }

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

    private var isActivityActive = true

    @SuppressLint("SetTextI18s")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        isActivityActive = true
        checkLoginStatus()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
        isActivityActive = false
        super.onDestroy()
    }

    private fun checkLoginStatus() {
        Log.d(TAG, "Checking login status")
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn && token != null) {
            Log.d(TAG, "Token found, verifying...$token")
            verifyTokenAndRedirect(token)
        } else {
            Log.d(TAG, "No token found, showing login UI")
            setupLoginUI()
        }
    }

    private fun verifyTokenAndRedirect(token: String) {
        lifecycleScope.launch {
            try {
                Log.d(TAG, "Verifying token...")
                meViewModel.fetchUserProfile { message, usuario ->
                    if (!isActivityActive) {
                        Log.w(TAG, "Activity no longer active during token verification")
                        return@fetchUserProfile
                    }

                    lifecycleScope.launch(Dispatchers.Main) {
                        if (usuario != null) {
                            Log.d(TAG, "Token verification successful")
                            Handler(Looper.getMainLooper()).postDelayed({
                                navigateToPrincipal()
                            }, 100)
                        } else {
                            Log.w(TAG, "Token verification failed: $message")
                            clearLoginState()
                            setupLoginUI()
                            showTokenExpiredMessage()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error during token verification", e)
                lifecycleScope.launch(Dispatchers.Main) {
                    clearLoginState()
                    setupLoginUI()
                    showToast("Error de verificación: ${e.message}")
                }
            }
        }
    }

    private fun setupLoginUI() {
        if (!isActivityActive) {
            Log.w(TAG, "Attempted to setup UI when activity is not active")
            return
        }

        runOnUiThread {
            try {
                Log.d(TAG, "Setting up login UI")
                enableEdgeToEdge()
                setContentView(R.layout.login_activity)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                initializeViews()
                setupTextWatchers()
                setupClickListeners()
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up UI", e)
                showToast("Error al inicializar la interfaz")
            }
        }
    }

    private fun initializeViews() {
        Log.d(TAG, "Initializing views")
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
        mail.addTextChangedListener { validateEmail() }
        password.addTextChangedListener { validatePassword() }
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
            showToast("Por favor, complete todos los campos")
            return
        }

        buttonSignIn.isEnabled = false
        Log.d(TAG, "Attempting login for email: $email")

        lifecycleScope.launch {
            try {
                loginViewModel.login(Login(email, pass)) { message, token ->
                    lifecycleScope.launch(Dispatchers.Main) {
                        buttonSignIn.isEnabled = true

                        if (token != null) {
                            Log.d(TAG, "Login successful")
                            saveLoginState(token)
                            showToast("Inicio de sesión exitoso")
                            meSave()
                            Handler(Looper.getMainLooper()).postDelayed({
                                if (isActivityActive) {
                                    navigateToPrincipal()
                                }
                            }, 500)
                        } else {
                            Log.w(TAG, "Login failed: $message")
                            showToast(message ?: "Error en el inicio de sesión")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error during login", e)
                lifecycleScope.launch(Dispatchers.Main) {
                    buttonSignIn.isEnabled = true
                    showToast("Error de conexión: ${e.message}")
                }
            }
        }
    }

    private fun saveLoginState(token: String) {
        try {
            Log.d(TAG, "Saving login state")
            saveTokenSecurely(this, token)
            getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().apply {
                putBoolean("isLoggedIn", true)
                putString("token", token)
                apply()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving login state", e)
            showToast("Error al guardar la sesión")
        }
    }

    private fun clearLoginState() {
        Log.d(TAG, "Clearing login state")
        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply()
    }

    private fun navigateToPrincipal() {
        if (!isActivityActive) {
            Log.w(TAG, "Attempted to navigate when activity is not active")
            return
        }

        try {
            Log.d(TAG, "Navigating to PrincipalActivity")
            val intent = Intent(this, PrincipalActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("status", 1)
            }
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error navigating to PrincipalActivity", e)
            showToast("Error al navegar a la pantalla principal")
        }
    }

    private fun navigateToRegister() {
        if (!isActivityActive) return
        Log.d(TAG, "Navigating to RegisterActivity")
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun showDevelopmentMessage() {
        showToast("En desarrollo")
    }

    private fun showTokenExpiredMessage() {
        showToast("Su sesión ha expirado. Por favor, inicie sesión nuevamente")
    }

    private fun showToast(message: String) {
        if (isActivityActive) {
            runOnUiThread {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun meSave() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "Fetching user profile")
                meViewModel.fetchUserProfile { message, usuario ->
                    if (usuario != null) {
                        Log.d(TAG, "User profile fetched successfully")
                    } else {
                        Log.w(TAG, "Failed to fetch user profile: $message")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching user profile", e)
            }
        }
    }
}
