package com.example.primer_parcial.domain.service

import com.example.primer_parcial.domain.model.Autor
import com.example.primer_parcial.domain.model.Categoria
import com.example.primer_parcial.domain.model.Libro
import com.example.primer_parcial.domain.repository.AutorRepository
import com.example.primer_parcial.domain.repository.CategoriaRepository
import com.example.primer_parcial.domain.repository.LibroRepository

/**
 * Servicio de dominio encargado de gestionar las consultas del catálogo de libros, autores y categorías.
 */
class CatalogoService(
    private val libroRepository: LibroRepository,
    private val autorRepository: AutorRepository,
    private val categoriaRepository: CategoriaRepository
) {
    fun listarLibros(): List<Libro> = libroRepository.obtenerTodos()

    fun listarAutores(): List<Autor> = autorRepository.obtenerTodos()

    fun listarCategorias(): List<Categoria> = categoriaRepository.obtenerTodas()

    fun librosPorCategoria(idCategoria: Int): List<Libro> = libroRepository.buscarPorCategoria(idCategoria)
}
