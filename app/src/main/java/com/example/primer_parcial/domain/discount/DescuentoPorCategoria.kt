package com.example.primer_parcial.domain.discount

import com.example.primer_parcial.domain.model.Categoria
import com.example.primer_parcial.domain.model.Libro

/**
 * Política de descuento que aplica un porcentaje de descuento únicamente si el libro pertenece a una categoría específica.
 */
class DescuentoPorCategoria(
    val categoria: Categoria,
    val porcentaje: Double
) : PoliticaDescuento {

    init {
        require(porcentaje in 0.0..100.0) { "El porcentaje de descuento debe estar entre 0.0 y 100.0" }
    }

    override fun aplicar(libro: Libro): Double {
        return if (libro.categoria.id == categoria.id) {
            val descuento = libro.precio * (porcentaje / 100.0)
            maxOf(0.0, libro.precio - descuento)
        } else {
            libro.precio
        }
    }
}
