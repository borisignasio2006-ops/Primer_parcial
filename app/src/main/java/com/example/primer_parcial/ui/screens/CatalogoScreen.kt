package com.example.primer_parcial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.primer_parcial.domain.discount.DescuentoPorcentaje
import com.example.primer_parcial.domain.discount.SinDescuento
import com.example.primer_parcial.domain.service.CatalogoService
import com.example.primer_parcial.ui.components.LibroCard

/**
 * Pantalla que muestra el catálogo de libros con filtros por categoría y opción de descuento.
 */
@Composable
fun CatalogoScreen(
    catalogoService: CatalogoService,
    modifier: Modifier = Modifier
) {
    var categoriaSeleccionadaId by rememberSaveable { mutableStateOf<Int?>(null) }
    var aplicarDescuento by rememberSaveable { mutableStateOf(false) }

    val categorias = catalogoService.listarCategorias()
    val libros = if (categoriaSeleccionadaId == null) {
        catalogoService.listarLibros()
    } else {
        catalogoService.librosPorCategoria(categoriaSeleccionadaId!!)
    }

    val politicaDescuento = if (aplicarDescuento) {
        DescuentoPorcentaje(10.0)
    } else {
        SinDescuento()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Catálogo de Libros",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Filtros por Categoría (LazyRow de FilterChip)
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = categoriaSeleccionadaId == null,
                        onClick = { categoriaSeleccionadaId = null },
                        label = { Text("Todas") }
                    )
                }
                items(categorias) { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionadaId == categoria.id,
                        onClick = { categoriaSeleccionadaId = categoria.id },
                        label = { Text(categoria.nombre) }
                    )
                }
            }
        }

        // Fila con Switch para descuento
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Aplicar 10% de descuento",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = aplicarDescuento,
                    onCheckedChange = { aplicarDescuento = it }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Lista de Libros
        items(libros, key = { it.id }) { libro ->
            LibroCard(
                libro = libro,
                politicaDescuento = politicaDescuento
            )
        }
    }
}
