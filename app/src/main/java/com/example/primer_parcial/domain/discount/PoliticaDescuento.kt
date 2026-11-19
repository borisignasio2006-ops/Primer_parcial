package com.example.primer_parcial.domain.discount

import com.example.primer_parcial.domain.model.Libro

/**
 * Interfaz que define el contrato para aplicar políticas de descuento sobre un libro (Patrón Strategy).
 */
interface PoliticaDescuento {
    /**
     * Aplica la política de descuento al libro y devuelve el precio final resultante (nunca negativo).
     */
    fun aplicar(libro: Libro): Double
}
