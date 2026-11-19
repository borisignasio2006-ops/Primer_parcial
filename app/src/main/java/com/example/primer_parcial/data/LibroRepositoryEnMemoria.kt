package com.example.primer_parcial.data

import com.example.primer_parcial.domain.model.Editorial
import com.example.primer_parcial.domain.model.Libro
import com.example.primer_parcial.domain.repository.AutorRepository
import com.example.primer_parcial.domain.repository.CategoriaRepository
import com.example.primer_parcial.domain.repository.LibroRepository

/**
 * Implementación en memoria del repositorio de Libros, que inyecta los repositorios de Autor y Categoria
 * para construir el catálogo inicial de libros.
 */
class LibroRepositoryEnMemoria(
    private val autorRepository: AutorRepository,
    private val categoriaRepository: CategoriaRepository
) : LibroRepository {

    private val editoriales = listOf(
        Editorial(id = 1, nombre = "Editorial Sudamericana", pais = "Argentina"),
        Editorial(id = 2, nombre = "Alfaguara", pais = "España")
    )

    private val libros: MutableList<Libro> by lazy {
        val autores = autorRepository.obtenerTodos()
        val categorias = categoriaRepository.obtenerTodas()

        val gabo = autores.find { it.id == 1 }!!
        val borges = autores.find { it.id == 2 }!!
        val isabel = autores.find { it.id == 3 }!!
        val cortazar = autores.find { it.id == 4 }!!

        val catNovela = categorias.find { it.id == 1 }!!
        val catCuento = categorias.find { it.id == 2 }!!
        val catMagico = categorias.find { it.id == 3 }!!

        val edSudamericana = editoriales[0]
        val edAlfaguara = editoriales[1]

        mutableListOf(
            Libro(
                id = 1,
                isbn = "978-950-07-2035-9",
                titulo = "Cien años de soledad",
                autor = gabo,
                categoria = catMagico,
                editorial = edSudamericana,
                precio = 120.0,
                stockInicial = 10,
                anioPublicacion = 1967
            ),
            Libro(
                id = 2,
                isbn = "978-950-07-0012-2",
                titulo = "El amor en los tiempos del cólera",
                autor = gabo,
                categoria = catNovela,
                editorial = edAlfaguara,
                precio = 145.5,
                stockInicial = 5,
                anioPublicacion = 1985
            ),
            Libro(
                id = 3,
                isbn = "978-950-04-1234-5",
                titulo = "Ficciones",
                autor = borges,
                categoria = catCuento,
                editorial = edSudamericana,
                precio = 85.0,
                stockInicial = 0, // Libro sin stock para probar estado "agotado"
                anioPublicacion = 1944
            ),
            Libro(
                id = 4,
                isbn = "978-950-04-5678-9",
                titulo = "El Aleph",
                autor = borges,
                categoria = catCuento,
                editorial = edAlfaguara,
                precio = 90.0,
                stockInicial = 8,
                anioPublicacion = 1949
            ),
            Libro(
                id = 5,
                isbn = "978-956-258-001-1",
                titulo = "La casa de los espíritus",
                autor = isabel,
                categoria = catNovela,
                editorial = edAlfaguara,
                precio = 130.0,
                stockInicial = 12,
                anioPublicacion = 1982
            ),
            Libro(
                id = 6,
                isbn = "978-956-258-045-5",
                titulo = "Paula",
                autor = isabel,
                categoria = catNovela,
                editorial = edSudamericana,
                precio = 110.0,
                stockInicial = 4,
                anioPublicacion = 1994
            ),
            Libro(
                id = 7,
                isbn = "978-950-515-102-1",
                titulo = "Rayuela",
                autor = cortazar,
                categoria = catNovela,
                editorial = edSudamericana,
                precio = 160.0,
                stockInicial = 7,
                anioPublicacion = 1963
            ),
            Libro(
                id = 8,
                isbn = "978-950-515-204-2",
                titulo = "Bestiario",
                autor = cortazar,
                categoria = catCuento,
                editorial = edAlfaguara,
                precio = 75.0,
                stockInicial = 15,
                anioPublicacion = 1951
            )
        )
    }

    override fun obtenerTodos(): List<Libro> = libros.toList()

    override fun obtenerPorId(id: Int): Libro? = libros.find { it.id == id }

    override fun buscarPorCategoria(idCategoria: Int): List<Libro> = libros.filter { it.categoria.id == idCategoria }
}
