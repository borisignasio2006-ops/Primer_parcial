package com.example.primer_parcial.data

import com.example.primer_parcial.domain.model.Autor
import com.example.primer_parcial.domain.repository.AutorRepository

/**
 * Implementación en memoria del repositorio de Autores.
 */
class AutorRepositoryEnMemoria : AutorRepository {
    private val autores = listOf(
        Autor(
            id = 1,
            nombre = "Gabriel García Márquez",
            nacionalidad = "Colombiana",
            biografia = "Escritor y periodista colombiano, considerado uno de los grandes maestros de la literatura universal y principal exponente del realismo mágico."
        ),
        Autor(
            id = 2,
            nombre = "Jorge Luis Borges",
            nacionalidad = "Argentina",
            biografia = "Destacado escritor argentino, reconocido por sus cuentos fantásticos, ensayos y poemas que exploran los laberintos, el tiempo y la eternidad."
        ),
        Autor(
            id = 3,
            nombre = "Isabel Allende",
            nacionalidad = "Chilena",
            biografia = "Escritora chilena, autora de exitosas novelas que combinan la realidad y la fantasía, siendo una de las novelistas iberoamericanas más leídas del mundo."
        ),
        Autor(
            id = 4,
            nombre = "Julio Cortázar",
            nacionalidad = "Argentina",
            biografia = "Escritor y traductor argentino, maestro del cuento corto y de la novela moderna, famoso por obras experimentales como Rayuela."
        )
    )

    override fun obtenerTodos(): List<Autor> = autores

    override fun obtenerPorId(id: Int): Autor? = autores.find { it.id == id }
}
