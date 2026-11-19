package com.example.primer_parcial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primer_parcial.domain.discount.DescuentoPorcentaje
import com.example.primer_parcial.domain.discount.PoliticaDescuento
import com.example.primer_parcial.domain.model.Autor
import com.example.primer_parcial.domain.model.Categoria
import com.example.primer_parcial.domain.model.Editorial
import com.example.primer_parcial.domain.model.Libro

/**
 * Componente visual que representa la tarjeta de información de un Libro, mostrando precios, stock y descuento.
 */
@Composable
fun LibroCard(
    libro: Libro,
    politicaDescuento: PoliticaDescuento,
    modifier: Modifier = Modifier
) {
    val precioOriginal = libro.precio
    val precioFinal = libro.precioFinal(politicaDescuento)
    val tieneDescuento = precioFinal < precioOriginal

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = libro.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = libro.autor.nombreCompleto(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Indicador de Stock o Agotado
                if (libro.hayStock()) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Stock: ${libro.stock}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Agotado",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${libro.categoria.nombre} · ${libro.editorial.nombre} · ${libro.anioPublicacion}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (tieneDescuento) {
                    Text(
                        text = "Bs %.2f".format(precioOriginal),
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = "Bs %.2f".format(precioFinal),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibroCardPreview() {
    MaterialTheme {
        val libroEjemplo = Libro(
            id = 1,
            isbn = "978-950-07-2035-9",
            titulo = "Cien años de soledad",
            autor = Autor(1, "Gabriel García Márquez", "Colombiana", "Bio..."),
            categoria = Categoria(1, "Realismo Mágico", "Desc..."),
            editorial = Editorial(1, "Sudamericana", "Argentina"),
            precio = 120.0,
            stockInicial = 10,
            anioPublicacion = 1967
        )
        Column(modifier = Modifier.padding(16.dp)) {
            LibroCard(libro = libroEjemplo, politicaDescuento = DescuentoPorcentaje(10.0))
        }
    }
}
