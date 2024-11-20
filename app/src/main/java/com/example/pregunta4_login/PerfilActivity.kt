package com.example.pregunta4_login

import android.content.Context
import android.content.Intent
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
import com.example.pregunta4_login.databinding.ActivityPerfilBinding
import com.example.pregunta4_login.services.ApiServiceFactory
import com.example.pregunta4_login.sql.ProfileImageDatabaseHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private lateinit var profileImageDbHelper: ProfileImageDatabaseHelper

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

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
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
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun handleImageLoading(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(filesDir, "profile_image.jpg")

            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            profileImageDbHelper.saveImagePath(file.absolutePath)
            binding.profileImage.setImageURI(Uri.fromFile(file))

            // Intenta subir la imagen al servidor (opcional)
            uploadImage(Uri.fromFile(file))
        } catch (e: Exception) {
            //Toast.makeText(this, "Error al procesar la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(uri: Uri) {
        val file = File(uri.path!!)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        lifecycleScope.launch {
            try {
                val response = ApiServiceFactory.updatePhoto?.uploadImage(body)
                if (response?.isSuccessful == true) {
                    Toast.makeText(this@PerfilActivity, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PerfilActivity, "Error al subir la imagen al servidor", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PerfilActivity, "Error al subir la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        profileImageDbHelper.close()
    }
}