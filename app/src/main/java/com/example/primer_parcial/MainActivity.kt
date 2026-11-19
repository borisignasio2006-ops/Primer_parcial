package com.example.primer_parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.primer_parcial.data.AutorRepositoryEnMemoria
import com.example.primer_parcial.data.CategoriaRepositoryEnMemoria
import com.example.primer_parcial.data.LibroRepositoryEnMemoria
import com.example.primer_parcial.domain.service.CatalogoService
import com.example.primer_parcial.domain.service.InventarioService
import com.example.primer_parcial.ui.LibreriaApp
import com.example.primer_parcial.ui.theme.Primer_parcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inversión de dependencias y composición de servicios (Principio D de SOLID)
        val autorRepository = AutorRepositoryEnMemoria()
        val categoriaRepository = CategoriaRepositoryEnMemoria()
        val libroRepository = LibroRepositoryEnMemoria(autorRepository, categoriaRepository)

        val catalogoService = CatalogoService(libroRepository, autorRepository, categoriaRepository)
        @Suppress("UNUSED_VARIABLE")
        val inventarioService = InventarioService(libroRepository)

        setContent {
            Primer_parcialTheme {
                LibreriaApp(catalogoService = catalogoService)
            }
        }
    }
}
