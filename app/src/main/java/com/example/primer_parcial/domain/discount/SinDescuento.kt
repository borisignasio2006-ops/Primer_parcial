package com.example.primer_parcial.domain.discount

import com.example.primer_parcial.domain.model.Libro

/**
 * Política de descuento que no aplica ninguna reducción, devolviendo el precio original del libro.
 */
class SinDescuento : PoliticaDescuento {
    override fun aplicar(libro: Libro): Double = libro.precio
}
