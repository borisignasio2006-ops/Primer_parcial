package com.example.primer_parcial.domain.discount

import com.example.primer_parcial.domain.model.Libro

/**
 * Política de descuento que aplica un porcentaje fijo de descuento sobre el precio del libro.
 */
class DescuentoPorcentaje(
    val porcentaje: Double
) : PoliticaDescuento {

    init {
        require(porcentaje in 0.0..100.0) { "El porcentaje de descuento debe estar entre 0.0 y 100.0" }
    }

    override fun aplicar(libro: Libro): Double {
        val descuento = libro.precio * (porcentaje / 100.0)
        return maxOf(0.0, libro.precio - descuento)
    }
}
