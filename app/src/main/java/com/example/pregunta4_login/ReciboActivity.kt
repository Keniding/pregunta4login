package com.example.pregunta4_login

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregunta4_login.adapter.RecibosAdapter
import com.example.pregunta4_login.databinding.ActivityReciboBinding
import com.example.pregunta4_login.ui.viewmodel.RecibosViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class ReciboActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReciboBinding
    private lateinit var adapter: RecibosAdapter
    private val viewModel: RecibosViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReciboBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    Toast.makeText(this,"En desarrollo", Toast.LENGTH_SHORT).show();
                    true
                }
                R.id.nav_notifications->{
                    Toast.makeText(this,"En desarrollo", Toast.LENGTH_SHORT).show();
                    true
                }
                else->false
            }
        }

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()

        val dni = sharedPreferences.getString("dni", "") ?: ""
        if (dni.isNotEmpty()) {
            viewModel.cargarRecibosByDni(dni)
        } else {
            Snackbar.make(binding.root, "No se encontró el DNI del usuario", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = RecibosAdapter().apply {
            setOnPdfClickListener { url ->
                abrirPdf(url)
            }
        }

        binding.recyclerViewRecibos.apply {
            layoutManager = LinearLayoutManager(this@ReciboActivity)
            adapter = this@ReciboActivity.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            val dni = sharedPreferences.getString("dni", "") ?: ""
            if (dni.isNotEmpty()) {
                viewModel.cargarRecibosByDni(dni)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.recibos.observe(this) { recibos ->
            adapter.submitList(recibos)
            binding.tvEmpty.visibility = if (recibos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun abrirPdf(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            Snackbar.make(binding.root, "No se pudo abrir el PDF", Snackbar.LENGTH_SHORT).show()
        }
    }
}
