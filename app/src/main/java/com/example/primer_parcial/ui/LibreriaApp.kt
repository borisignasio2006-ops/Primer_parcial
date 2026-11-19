package com.example.primer_parcial.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.primer_parcial.domain.service.CatalogoService
import com.example.primer_parcial.ui.components.*
import com.example.primer_parcial.ui.screens.AutoresScreen
import com.example.primer_parcial.ui.screens.CatalogoScreen

/**
 * Componente raíz de la interfaz de usuario que gestiona el Scaffold, TopBar y la barra de navegación inferior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibreriaApp(catalogoService: CatalogoService) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (selectedTab == 0) "Librería - Catálogo" else "Librería - Autores")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Catálogo") },
                    label = { Text("Catálogo") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Autores") },
                    label = { Text("Autores") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        if (selectedTab == 0) {
            CatalogoScreen(catalogoService = catalogoService, modifier = contentModifier)
        } else {
            AutoresScreen(catalogoService = catalogoService, modifier = contentModifier)
        }
    }
}
