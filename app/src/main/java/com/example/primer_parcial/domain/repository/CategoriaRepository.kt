package com.example.primer_parcial.domain.repository

import com.example.primer_parcial.domain.model.Categoria

/**
 * Interfaz de repositorio para la gestión de acceso a datos de Categorías (Principio de Segregación de Interfaces).
 */
interface CategoriaRepository {
    fun obtenerTodas(): List<Categoria>
}
