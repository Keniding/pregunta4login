package com.example.pregunta4_login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregunta4_login.adapter.IncidenciasAdapter
import com.example.pregunta4_login.databinding.ActivityReporteBinding
import com.example.pregunta4_login.ui.viewmodel.ReporteViewModel

class ReporteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReporteBinding
    private lateinit var viewModel: ReporteViewModel
    private lateinit var adapter: IncidenciasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        adapter = IncidenciasAdapter()
        binding.rvIncidencias.apply {
            layoutManager = LinearLayoutManager(this@ReporteActivity)
            adapter = this@ReporteActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ReporteViewModel::class.java]

        viewModel.reportes.observe(this) { reportes ->
            Log.d("ReporteActivity", "Recibidos ${reportes.size} reportes")
            adapter.setIncidencias(reportes)
            binding.tvError.visibility = View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Log.e("ReporteActivity", "Error: $it")
                binding.tvError.apply {
                    text = it
                    visibility = View.VISIBLE
                }
            }
        }

        // Cargar los reportes
        viewModel.cargarReportes()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.cargarReportes()
        }
    }
}
