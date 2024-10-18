package com.example.pregunta4_login

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PerfilActivity : AppCompatActivity() {


    private val VIDEO_ID = "ArY6njyJKDY"
    private val PLAYLIST_ID = "RDM_Dk9demut0"


    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Configurar ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Enlazar vistas
        //val welcomeTextView: TextView = findViewById(R.id.tv_welcome)
        val userNameTextView: TextView = findViewById(R.id.tv_user_name)
        val userPhotoImageView: ImageView = findViewById(R.id.user_photo)
        //val presentationVideoView: VideoView = findViewById(R.id.presentation_video)
        val recyclerViewNextDates: RecyclerView = findViewById(R.id.recycler_view_next_dates)
        val shareLocationButton: Button = findViewById(R.id.btn_share_location)

        // Recuperar el nombre del usuario desde SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("nombre", "Usuario")

        // Mostrar el nombre del usuario en el mensaje de bienvenida
        userNameTextView.text = userName

        /*
        val webView: WebView = findViewById(R.id.webview_youtube)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Construir la URL del video con la lista de reproducción
        val videoUrl = "https://www.youtube.com/watch?v=EDbvvnxVCr0&list=RDM_Dk9demut0&index=5"
        val html = """
            <html>
                <body>
                    <iframe width="100%" height="100%" src="$videoUrl" frameborder="0" allowfullscreen></iframe>
                </body>
            </html>
        """.trimIndent()

        webView.loadData(html, "text/html", "utf-8")

         */

        val youtubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                //val videoId = "EDbvvnxVCr0" // id de video de YouTube
                // NAq3ysmpPcA
                val videoId = "NAq3ysmpPcA"
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

/*
// Configurar video de presentación
val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_video)
presentationVideoView.setVideoURI(videoUri)
presentationVideoView.setOnPreparedListener { it.start() }

 */

// Configurar botón para compartir ubicación
shareLocationButton.setOnClickListener {
    Toast.makeText(this, "Compartiendo ubicación...", Toast.LENGTH_SHORT).show()
    // Aquí deberías implementar la lógica para compartir la ubicación
}

}
}
