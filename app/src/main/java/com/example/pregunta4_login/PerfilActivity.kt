package com.example.pregunta4_login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.pregunta4_login.api.ApiServiceMe
import com.example.pregunta4_login.databinding.ActivityPerfilBinding
import com.example.pregunta4_login.models.UpdateModelMe
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.sql.ProfileImageDatabaseHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private lateinit var profileImageDbHelper: ProfileImageDatabaseHelper

    private var isActive = false

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                handleImageLoading(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileImageDbHelper = ProfileImageDatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = sharedPreferences.getString("nombre", "Usuario")

        binding.tvUserName.text = userName

        // Cargar la imagen guardada si existe
        val savedImagePath = profileImageDbHelper.getImagePath()
        if (savedImagePath != null) {
            binding.profileImage.setImageURI(Uri.parse(savedImagePath))
        } else {
            binding.profileImage.setImageResource(R.drawable.usuario_)
        }

        binding.fabChangeAvatar.setOnClickListener {
            openImagePicker()
        }

        binding.profileImage.setOnClickListener {
            openImagePicker()
        }

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "NAq3ysmpPcA"
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        binding.btnShareLocation.setOnClickListener {
            Toast.makeText(this, "Compartiendo ubicación...", Toast.LENGTH_SHORT).show()
        }

        ApiServiceFactory.initializeUpdatePhoto(this)
        ApiServiceFactory.initializeMeInstance(this)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun handleImageLoading(uri: Uri) {
        try {
            // Crear un archivo temporal con nombre único
            val timestamp = System.currentTimeMillis()
            val tempFile = File(filesDir, "profile_image_$timestamp.jpg")

            contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // Actualizar la UI inmediatamente con la imagen temporal
            binding.profileImage.setImageURI(Uri.fromFile(tempFile))

            // Subir la imagen al servidor
            uploadImage(tempFile)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al procesar la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(file: File) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                val response = ApiServiceFactory.updatePhoto?.uploadImage(body)

                withContext(Dispatchers.Main) {
                    when {
                        response?.isSuccessful == true -> {
                            response.body()?.let { imageResponse ->
                                val imageUrl = imageResponse.url

                                // Guardar la URL y actualizar SharedPreferences
                                sharedPreferences.edit()
                                    .putString("profile_image_url", imageUrl)
                                    .apply()

                                // Guardar la ruta del archivo local
                                profileImageDbHelper.saveImagePath(file.absolutePath)

                                // Actualizar el perfil con la nueva URL
                                updateImagePerfil(imageUrl)

                                Toast.makeText(
                                    this@PerfilActivity,
                                    "Imagen subida exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        response?.code() == 404 -> {
                            Toast.makeText(
                                this@PerfilActivity,
                                "URL no encontrada",
                                Toast.LENGTH_LONG
                            ).show()
                            // Limpiar el archivo temporal en caso de error
                            file.delete()
                        }
                        else -> {
                            Toast.makeText(
                                this@PerfilActivity,
                                "Error: ${response?.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                            // Limpiar el archivo temporal en caso de error
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PerfilActivity,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    // Limpiar el archivo temporal en caso de error
                    file.delete()
                }
            }
        }
    }

    private suspend fun updateImagePerfil(imageUrl: String) {
        withContext(Dispatchers.IO) {
            try {
                val dni = sharedPreferences.getString("dni", "") ?: ""
                val idRol = sharedPreferences.getInt("id_rol", 0)
                val nombre = sharedPreferences.getString("nombre", "") ?: ""
                val email = sharedPreferences.getString("email", "") ?: ""

                val userMe = UpdateModelMe(
                    dni = dni,
                    nombre = nombre,
                    email = email,
                    image = imageUrl,
                    idRol = idRol
                )

                val response = ApiServiceFactory.meInstance?.updateUser(userMe)

                withContext(Dispatchers.Main) {
                    if (isActive) {
                        if (response?.isSuccessful == true) {
                            val mensaje = response.body()?.mensaje
                            Toast.makeText(this@PerfilActivity, mensaje, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this@PerfilActivity,
                                "Espere...: ${response?.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    if (isActive) {
                        Toast.makeText(
                            this@PerfilActivity,
                            "Espere...: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        profileImageDbHelper.close()
    }
}