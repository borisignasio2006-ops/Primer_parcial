package com.example.primer_parcial.domain.model

/**
 * Representa una categoría temática para clasificar los libros en la librería.
 */
data class Categoria(
    val id: Int,
    val nombre: String,
    val descripcion: String
)
