package com.example.pregunta4_login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregunta4_login.adapter.IncidenciasAdapter
import com.example.pregunta4_login.databinding.ActivityIncidenciaBinding
import com.example.pregunta4_login.ui.viewmodel.ReporteViewModel

class ReporteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncidenciaBinding
    private lateinit var adapter: IncidenciasAdapter
    private val viewModel: ReporteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()

        viewModel.cargarReportes()
    }

    private fun setupRecyclerView() {
        adapter = IncidenciasAdapter()
        binding.rvIncidencias.apply {
            layoutManager = LinearLayoutManager(this@ReporteActivity)
            adapter = this@ReporteActivity.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.cargarReportes()
        }
    }

    private fun observeViewModel() {
        viewModel.reportes.observe(this) { reportes ->
            adapter.setIncidencias(reportes)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                binding.tvError.apply {
                    text = it
                    visibility = View.VISIBLE
                }
            } ?: run {
                binding.tvError.visibility = View.GONE
            }
        }
    }
}
