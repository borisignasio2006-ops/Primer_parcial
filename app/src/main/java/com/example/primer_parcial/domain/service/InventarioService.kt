package com.example.primer_parcial.domain.service

import com.example.primer_parcial.domain.model.Libro
import com.example.primer_parcial.domain.repository.LibroRepository

/**
 * Servicio de dominio encargado de gestionar las operaciones de inventario (ventas, ingresos y control de stock).
 */
class InventarioService(
    private val libroRepository: LibroRepository
) {
    /**
     * Registra la venta de un libro, reduciendo su stock. Lanza NoSuchElementException si el libro no existe.
     */
    fun registrarVenta(idLibro: Int, cantidad: Int) {
        val libro = libroRepository.obtenerPorId(idLibro)
            ?: throw NoSuchElementException("No se encontró el libro con ID $idLibro")
        libro.reducirStock(cantidad)
    }

    /**
     * Registra el ingreso de stock para un libro. Lanza NoSuchElementException si el libro no existe.
     */
    fun registrarIngreso(idLibro: Int, cantidad: Int) {
        val libro = libroRepository.obtenerPorId(idLibro)
            ?: throw NoSuchElementException("No se encontró el libro con ID $idLibro")
        libro.aumentarStock(cantidad)
    }

    /**
     * Devuelve la lista de libros que se encuentran sin stock (stock == 0).
     */
    fun librosSinStock(): List<Libro> {
        return libroRepository.obtenerTodos().filter { !it.hayStock() }
    }
}
