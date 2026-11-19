# Diseño de Interfaz - Aplicación Librería

Este documento detalla el diseño de las dos pantallas principales de la aplicación de la librería, especificando sus objetivos, bocetos en ASCII, componentes de Jetpack Compose utilizados, y su relación con el modelo de clases y los principios SOLID.

---

## 1. Pantalla de Catálogo de Libros (Pantalla 1)

### Objeto del modelo que visualiza:
* **Libro** (y sus relaciones con **Autor**, **Categoria** y **Editorial**).

### Objetivo:
Permitir al usuario visualizar el inventario completo de libros de la librería, filtrarlos por categoría temática mediante chips de selección rápida, y simular la aplicación de un descuento del 10% sobre los precios de los libros en tiempo real.

### Boceto en ASCII:
```text
┌──────────────────────────────────────────┐
│          [ Librería - Catálogo ]         │
├──────────────────────────────────────────┤
│ Catálogo de Libros                       │
│                                          │
│ [Todas] [Novela] [Cuento] [Realismo Mág] │
│                                          │
│ Aplicar 10% de descuento             [O] │
├──────────────────────────────────────────┤
│ ┌──────────────────────────────────────┐ │
│ │ Cien años de soledad                 │ │
│ │ Gabriel García Márquez               │ │
│ │ Realismo Mágico · Sudamericana · 1967│ │
│ │                        Stock: 10     │ │
│ │                           Bs 120.00  │ │
│ └──────────────────────────────────────┘ │
│ ┌──────────────────────────────────────┐ │
│ │ Ficciones                            │ │
│ │ Jorge Luis Borges                    │ │
│ │ Cuento · Sudamericana · 1944         │ │
│ │                           [ Agotado] │ │
│ │   Bs 85.00                           │ │
│ └──────────────────────────────────────┘ │
├──────────────────────────────────────────┤
│   🏠 Catálogo             👤 Autores     │
└──────────────────────────────────────────┘
```

### Componentes de Jetpack Compose utilizados:
* **Scaffold**: Contenedor estructural principal que organiza la barra superior (`CenterAlignedTopAppBar`), la barra de navegación inferior (`NavigationBar`) y el contenido de la pantalla.
* **CenterAlignedTopAppBar**: Barra superior centrada que muestra el título de la sección actual.
* **NavigationBar**: Barra de navegación inferior que aloja los elementos de cambio de sección.
* **NavigationBarItem**: Elemento individual dentro de la barra de navegación para alternar entre "Catálogo" y "Autores".
* **LazyColumn**: Contenedor desplazable verticalmente eficiente que lista los elementos del catálogo y los filtros de manera optimizada.
* **LazyRow**: Contenedor desplazable horizontalmente que muestra los filtros de categorías.
* **FilterChip**: Chip interactivo utilizado para seleccionar la categoría de filtrado ("Todas" y las categorías dinámicas).
* **Switch**: Control deslizante booleano para activar o desactivar la aplicación del 10% de descuento.
* **Card**: Tarjeta contenedora con esquinas redondeadas y elevación que encapsula la información de cada libro.
* **Row** y **Column**: Contenedores de diseño flexible (fila y columna) para alinear elementos textuales e indicadores de stock.
* **Spacer**: Componente de separación para añadir espacios verticales u horizontales entre elementos.
* **Icon**: Renderizado de vectores gráficos nativos (`Icons.Filled.Home`, `Icons.Filled.Person`).
* **Text**: Componente tipográfico para renderizar títulos, precios, autores y metadatos utilizando las escalas de `MaterialTheme.typography`.
* **Surface**: Contenedor con color de fondo personalizado para destacar las etiquetas de stock ("Stock: N" o "Agotado").

---

## 2. Pantalla de Autores (Pantalla 2)

### Objeto del modelo que visualiza:
* **Autor**.

### Objetivo:
Presentar la nómina de autores literarios destacados cuyas obras forman parte del catálogo de la librería, mostrando sus datos biográficos y nacionalidad.

### Boceto en ASCII:
```text
┌──────────────────────────────────────────┐
│           [ Librería - Autores ]         │
├──────────────────────────────────────────┤
│ Autores                                  │
│                                          │
│ ┌──────────────────────────────────────┐ │
│ │ Gabriel García Márquez               │ │
│ │ Nacionalidad: Colombiana             │ │
│ │ Escritor y periodista colombiano,    │ │
│ │ considerado uno de los grandes...    │ │
│ └──────────────────────────────────────┘ │
│ ┌──────────────────────────────────────┐ │
│ │ Jorge Luis Borges                    │ │
│ │ Nacionalidad: Argentina              │ │
│ │ Destacado escritor argentino,        │ │
│ │ reconocido por sus cuentos...        │ │
│ └──────────────────────────────────────┘ │
├──────────────────────────────────────────┤
│   🏠 Catálogo             👤 Autores     │
└──────────────────────────────────────────┘
```

### Componentes de Jetpack Compose utilizados:
* **Scaffold**: Estructura de diseño base compartida con el resto de la aplicación.
* **CenterAlignedTopAppBar**: Barra superior centrada con el título de autores.
* **NavigationBar** y **NavigationBarItem**: Sistema de navegación inferior persistente.
* **LazyColumn**: Lista vertical optimizada para mostrar eficientemente las tarjetas de autores.
* **Card**: Tarjeta contenedora para aislar visualmente la información de cada autor.
* **Column**: Contenedor vertical para organizar el nombre, nacionalidad y biografía del autor.
* **Spacer**: Elemento espaciador para separar visualmente los distintos bloques de texto.
* **Text**: Componente de texto parametrizado mediante el sistema de diseño (`MaterialTheme.typography`).

---

## Relación con el modelo de clases y SOLID

* **S (Responsabilidad Única):** Las pantallas (`CatalogoScreen`, `AutoresScreen`) y componentes visuales (`LibroCard`, `AutorCard`) se limitan estrictamente a renderizar la interfaz de usuario y delegar la interacción al estado local o servicios. No contienen lógica de negocio de dominio.
* **O (Abierto/Cerrado):** El cálculo de precios utiliza el patrón *Strategy* (`PoliticaDescuento`) implementado en el dominio, permitiendo aplicar descuentos (`SinDescuento` o `DescuentoPorcentaje`) sin alterar la lógica interna de los componentes visuales ni del modelo `Libro`.
* **L (Sustitución de Liskov):** Las políticas de descuento (`SinDescuento`, `DescuentoPorcentaje`) son completamente intercambiables al calcular el precio final del libro mediante `libro.precioFinal(politica)`.
* **I (Segregación de Interfaces):** Los composables reciben únicamente los datos o servicios necesarios (`CatalogoService`, `Libro`, `Autor`), desacoplando la UI de repositorios y detalles de infraestructura innecesarios.
* **D (Inversión de Dependencias):** MainActivity actúa como la raíz de composición pura, instanciando los repositorios concretos y los servicios de dominio a través de abstracciones (interfaces), asegurando que los componentes de presentación y dominio desconozcan los detalles de persistencia en memoria.
