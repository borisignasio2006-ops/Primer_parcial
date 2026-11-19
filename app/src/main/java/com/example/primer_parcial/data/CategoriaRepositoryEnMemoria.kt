package com.example.primer_parcial.data

import com.example.primer_parcial.domain.model.Categoria
import com.example.primer_parcial.domain.repository.CategoriaRepository

/**
 * Implementación en memoria del repositorio de Categorías.
 */
class CategoriaRepositoryEnMemoria : CategoriaRepository {
    private val categorias = listOf(
        Categoria(
            id = 1,
            nombre = "Novela",
            descripcion = "Narraciones literarias de extensión y argumento extensos en prosa."
        ),
        Categoria(
            id = 2,
            nombre = "Cuento",
            descripcion = "Relatos breves de ficción con un desarrollo argumental conciso."
        ),
        Categoria(
            id = 3,
            nombre = "Realismo Mágico",
            descripcion = "Género literario que incorpora elementos mágicos o fantásticos en situaciones realistas."
        )
    )

    override fun obtenerTodas(): List<Categoria> = categorias
}
