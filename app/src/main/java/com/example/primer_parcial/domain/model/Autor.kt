package com.example.primer_parcial.domain.model

/**
 * Representa a un autor de libros dentro del dominio de la librería.
 */
data class Autor(
    val id: Int,
    val nombre: String,
    val nacionalidad: String,
    val biografia: String
) {
    /**
     * Devuelve el nombre completo del autor.
     */
    fun nombreCompleto(): String = nombre
}
