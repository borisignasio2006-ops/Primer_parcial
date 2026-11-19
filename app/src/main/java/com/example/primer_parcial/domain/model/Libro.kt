package com.example.primer_parcial.domain.model

import com.example.primer_parcial.domain.discount.PoliticaDescuento

/**
 * Representa un libro disponible en el inventario de la librería, gestionando sus reglas de negocio
 * relativas a precios, stock y asociaciones con autor, categoría y editorial.
 */
class Libro(
    val id: Int,
    val isbn: String,
    val titulo: String,
    val autor: Autor,
    val categoria: Categoria,
    val editorial: Editorial,
    val precio: Double,
    stockInicial: Int,
    val anioPublicacion: Int
) {
    var stock: Int = stockInicial
        private set

    init {
        require(precio > 0) { "El precio del libro debe ser mayor a 0" }
        require(stock >= 0) { "El stock inicial no puede ser negativo" }
    }

    /**
     * Verifica si hay stock disponible del libro.
     */
    fun hayStock(): Boolean = stock > 0

    /**
     * Reduce el stock del libro en la cantidad especificada.
     */
    fun reducirStock(cantidad: Int) {
        require(cantidad > 0) { "La cantidad a reducir debe ser mayor a 0" }
        require(cantidad <= stock) { "No hay suficiente stock disponible para reducir" }
        stock -= cantidad
    }

    /**
     * Aumenta el stock del libro en la cantidad especificada.
     */
    fun aumentarStock(cantidad: Int) {
        require(cantidad > 0) { "La cantidad a aumentar debe ser mayor a 0" }
        stock += cantidad
    }

    /**
     * Calcula el precio final del libro aplicando una política de descuento.
     */
    fun precioFinal(politica: PoliticaDescuento): Double = politica.aplicar(this)
}
