package com.example.primer_parcial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.primer_parcial.domain.service.CatalogoService
import com.example.primer_parcial.ui.components.AutorCard

/**
 * Pantalla que muestra el listado de autores registrados en la librería.
 */
@Composable
fun AutoresScreen(
    catalogoService: CatalogoService,
    modifier: Modifier = Modifier
) {
    val autores = catalogoService.listarAutores()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Autores",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(autores) { autor ->
            AutorCard(autor = autor)
        }
    }
}
