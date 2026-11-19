package com.example.primer_parcial.domain.repository

import com.example.primer_parcial.domain.model.Autor

/**
 * Interfaz de repositorio para la gestión de acceso a datos de Autores (Principio de Segregación de Interfaces).
 */
interface AutorRepository {
    fun obtenerTodos(): List<Autor>
    fun obtenerPorId(id: Int): Autor?
}
