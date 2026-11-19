package com.example.primer_parcial.domain.repository

import com.example.primer_parcial.domain.model.Libro

/**
 * Interfaz de repositorio para la gestión de acceso a datos de Libros (Principio de Segregación de Interfaces).
 */
interface LibroRepository {
    fun obtenerTodos(): List<Libro>
    fun obtenerPorId(id: Int): Libro?
    fun buscarPorCategoria(idCategoria: Int): List<Libro>
}
