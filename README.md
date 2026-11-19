# Primer Parcial - Programación III: Librería

Materia: Programación III  
Docente: Johnn Hidalgo  
Estudiante: Boris Ignacio Calcina Barriga  
Ingeniería de Software - Horario A  

Esta aplicación móvil desarrollada en Kotlin con Jetpack Compose implementa un sistema de gestión para una librería universitaria. Permite consultar el catálogo de libros, filtrar por categorías temáticas, aplicar políticas de descuento y revisar el listado de autores y sus biografías aplicando buenas prácticas de programación orientada a objetos y arquitectura limpia.

---

## Checkpoint 1: Diseño de clases (15 puntos)

Diseñé el modelo de clases conceptual estructurando el dominio de la librería alrededor de cuatro entidades principales con relaciones claras: un autor escribe muchos libros, una categoría agrupa muchos libros, y una editorial publica muchos libros. Cada libro se relaciona exactamente con un autor, una categoría y una editorial. 

Puedes revisar el diagrama de clases completo en el siguiente enlace: [Diagrama de Clases (Mermaid)](https://mermaid.ai/d/07e724ac-b222-48d3-9f75-407300fbf34c).

---

## Checkpoint 2: Implementación (10 puntos)

Implementé las clases en Kotlin organizadas en paquetes siguiendo principios de arquitectura limpia y POO:
- `domain.model`: Contiene `Autor`, `Categoria`, `Editorial` y `Libro`.
- `domain.discount`: Contiene la interfaz `PoliticaDescuento` y estrategias como `SinDescuento`, `DescuentoPorcentaje` y `DescuentoPorCategoria`.
- `domain.repository`: Define las interfaces `LibroRepository`, `AutorRepository` y `CategoriaRepository`.
- `data`: Contiene las implementaciones en memoria (`LibroRepositoryEnMemoria`, `AutorRepositoryEnMemoria`, `CategoriaRepositoryEnMemoria`).
- `domain.service`: Contiene `CatalogoService` e `InventarioService`.

Aplicación de los principios SOLID en el código:
- **S (Responsabilidad Única):** La clase `Libro` maneja sus propias reglas de negocio e invariantes de stock mediante validaciones en su bloque `init`.
- **O (Abierto/Cerrado):** El uso del patrón *Strategy* con `PoliticaDescuento` permite añadir nuevas reglas de descuento creando clases como `DescuentoPorcentaje` sin modificar la clase `Libro`.
- **L (Sustitución de Liskov):** Clases como `SinDescuento` y `DescuentoPorCategoria` sustituyen a `PoliticaDescuento` de forma transparente.
- **I (Segregación de Interfaces):** Las interfaces de repositorio están separadas (`LibroRepository`, `AutorRepository`, `CategoriaRepository`), asegurando que cada cliente dependa solo de lo que utiliza.
- **D (Inversión de Dependencias):** Los servicios de dominio dependen de interfaces de repositorio y `MainActivity` actúa como la única raíz de composición donde se instancian las implementaciones en memoria.

---

## Checkpoint 3: Scripts usados (10 puntos)

*CONTEXTO GENERAL DEL PROYECTO (léelo completo antes de escribir código)

Soy estudiante universitario (UPB, Programación III, docente Johnn Hidalgo) y estoy resolviendo un examen parcial. El caso de estudio elegido es una LIBRERÍA. Debo construir en Kotlin con Jetpack Compose una app Android con: (1) modelo de clases, (2) implementación de las clases aplicando POO, (3) scripts usados, (4) diseño de dos pantallas, (5) dos pantallas en Jetpack Compose que listen al menos dos de los objetos implementados, (6) que compile y corra en emulador. Todo se sube a un repo público de GitHub y se evalúan al menos 3 commits. Este es el PRIMER de tres prompts/commits; en cada uno tú empiezas sin memoria, por eso te doy todo el contexto.

PROYECTO BASE (ya existe, está abierto en Android Studio):
- Paquete raíz: com.example.primer_parcial
- Kotlin 2.2.10, Jetpack Compose con Material3, minSdk 24.
- Existe MainActivity.kt (con un Greeting de ejemplo) y el paquete ui.theme (Primer_parcialTheme). NO los modifiques en este commit.
- PROHIBIDO tocar build.gradle.kts, libs.versions.toml o agregar dependencias.

MODELO DE CLASES OFICIAL (no te desvíes de él, ni agregues ni quites clases):

Paquete domain.model:
- Autor (data class): id: Int, nombre: String, nacionalidad: String, biografia: String. Método: nombreCompleto(): String (devuelve el nombre).
- Categoria (data class): id: Int, nombre: String, descripcion: String.
- Editorial (data class): id: Int, nombre: String, pais: String.
- Libro (class normal, NO data class): id: Int, isbn: String, titulo: String, autor: Autor, categoria: Categoria, editorial: Editorial, precio: Double, stock: Int (var con private set), anioPublicacion: Int.
    - init: require(precio > 0) y require(stock >= 0) con mensajes claros.
    - hayStock(): Boolean
    - reducirStock(cantidad: Int): exige cantidad > 0 y cantidad <= stock; si no, IllegalArgumentException.
    - aumentarStock(cantidad: Int): exige cantidad > 0.
    - precioFinal(politica: PoliticaDescuento): Double = politica.aplicar(this)

Relaciones: un Autor escribe muchos Libros; una Categoria agrupa muchos Libros; una Editorial publica muchos Libros; cada Libro tiene exactamente un Autor, una Categoria y una Editorial.

Paquete domain.discount (patrón Strategy):
- interface PoliticaDescuento { fun aplicar(libro: Libro): Double } (devuelve el precio final del libro; NUNCA negativo).
- SinDescuento: devuelve libro.precio.
- DescuentoPorcentaje(porcentaje: Double): require(porcentaje en 0.0..100.0); devuelve precio con ese porcentaje de descuento.
- DescuentoPorCategoria(categoria: Categoria, porcentaje: Double): si libro.categoria.id == categoria.id aplica el porcentaje; si no, devuelve libro.precio. Mismo require del porcentaje.

Paquete domain.repository (solo interfaces, ISP):
- LibroRepository: obtenerTodos(): List<Libro>, obtenerPorId(id: Int): Libro?, buscarPorCategoria(idCategoria: Int): List<Libro>
- AutorRepository: obtenerTodos(): List<Autor>, obtenerPorId(id: Int): Autor?
- CategoriaRepository: obtenerTodas(): List<Categoria>

(Más adelante habrá: data -> LibroRepositoryEnMemoria, AutorRepositoryEnMemoria, CategoriaRepositoryEnMemoria; domain.service -> CatalogoService, InventarioService; y la UI. NO los hagas ahora.)

PRINCIPIOS SOLID QUE DEBES RESPETAR:
S: cada clase una sola responsabilidad (los modelos solo guardan datos y sus propias reglas).
O: nuevos descuentos se agregan creando clases nuevas que implementan PoliticaDescuento, sin tocar Libro.
L: cualquier PoliticaDescuento debe poder sustituir a otra sin romper nada.
I: interfaces de repositorio pequeñas y separadas.
D: lo de alto nivel dependerá de interfaces, no de clases concretas.

TAREA DE ESTE COMMIT (solo esto):
Crea en com.example.primer_parcial los paquetes domain.model, domain.discount y domain.repository con TODAS las clases e interfaces descritas arriba. Un archivo por clase o interfaz. Nombres exactamente como están (en español). Agrega KDoc breve en cada clase e interfaz explicando su responsabilidad.

NO HAGAS: implementaciones de repositorios, servicios, datos de ejemplo, UI, ViewModels, tests, ni cambios en archivos existentes.

AL TERMINAR: verifica que el proyecto compila, y dame (1) la lista de archivos creados y (2) un resumen de cómo se cumple cada principio SOLID en este código.*

---

**CONTEXTO GENERAL DEL PROYECTO (léelo completo antes de escribir código)

Soy estudiante universitario (UPB, Programación III, docente Johnn Hidalgo) y estoy resolviendo un examen parcial. El caso de estudio elegido es una LIBRERÍA. Debo construir en Kotlin con Jetpack Compose una app Android con: (1) modelo de clases, (2) implementación de las clases aplicando POO, (3) scripts usados, (4) diseño de dos pantallas, (5) dos pantallas en Jetpack Compose que listen al menos dos de los objetos implementados, (6) que compile y corra en emulador. Todo se sube a un repo público de GitHub y se evalúan al menos 3 commits. Este es el SEGUNDO de tres prompts/commits; tú empiezas sin memoria, por eso te doy todo el contexto.

PROYECTO BASE:
- Paquete raíz: com.example.primer_parcial
- Kotlin 2.2.10, Jetpack Compose con Material3, minSdk 24.
- PROHIBIDO tocar build.gradle.kts, libs.versions.toml o agregar dependencias.

MODELO DE CLASES OFICIAL (no te desvíes de él, ni agregues ni quites clases):

YA IMPLEMENTADO en el commit 1 (léelo en el proyecto antes de continuar, NO lo reescribas ni modifiques):
- domain.model: Autor(id, nombre, nacionalidad, biografia; nombreCompleto()), Categoria(id, nombre, descripcion), Editorial(id, nombre, pais), Libro(id, isbn, titulo, autor, categoria, editorial, precio, stock [var, private set], anioPublicacion; hayStock(), reducirStock(cantidad), aumentarStock(cantidad), precioFinal(politica)).
- domain.discount: PoliticaDescuento { aplicar(libro: Libro): Double }, SinDescuento, DescuentoPorcentaje(porcentaje), DescuentoPorCategoria(categoria, porcentaje).
- domain.repository: LibroRepository { obtenerTodos(), obtenerPorId(id): Libro?, buscarPorCategoria(idCategoria) }, AutorRepository { obtenerTodos(), obtenerPorId(id): Autor? }, CategoriaRepository { obtenerTodas() }.
  Relaciones: un Autor escribe muchos Libros; una Categoria agrupa muchos Libros; una Editorial publica muchos Libros; cada Libro tiene un Autor, una Categoria y una Editorial.

TAREA DE ESTE COMMIT (esto es lo que debes crear ahora):

1) Paquete data (implementaciones en memoria de las interfaces):
- AutorRepositoryEnMemoria : AutorRepository. Contiene una lista inmutable con al menos 4 autores reales (García Márquez, Jorge Luis Borges, Isabel Allende, Julio Cortázar, con id, nacionalidad y biografía de 1 o 2 oraciones).
- CategoriaRepositoryEnMemoria : CategoriaRepository. Al menos 3 categorías (por ejemplo Novela, Cuento, Realismo mágico), con descripción corta.
- LibroRepositoryEnMemoria : LibroRepository. Su constructor recibe un AutorRepository y un CategoriaRepository (inyección de dependencias por constructor, tipos = interfaces, NO las clases concretas) y busca los autores y categorías por id para construir los libros. Define internamente (private) 2 Editoriales de ejemplo. Crea 8 libros coherentes con esos autores y categorías (ISBN inventados, años reales aproximados, precios en bolivianos entre 60.0 y 250.0, stock entre 0 y 15; al menos UN libro con stock 0 para probar el estado "agotado"). Los libros se guardan en una MutableList privada y los métodos públicos devuelven List<Libro> (no exponer la lista mutable). Como Libro es mutable en su stock, las operaciones de InventarioService deben verse reflejadas al consultar de nuevo.

2) Paquete domain.service (reciben interfaces por constructor, NUNCA clases concretas):
- CatalogoService(libroRepository: LibroRepository, autorRepository: AutorRepository, categoriaRepository: CategoriaRepository) con: listarLibros(): List<Libro>, listarAutores(): List<Autor>, listarCategorias(): List<Categoria>, librosPorCategoria(idCategoria: Int): List<Libro>.
- InventarioService(libroRepository: LibroRepository) con: registrarVenta(idLibro: Int, cantidad: Int), registrarIngreso(idLibro: Int, cantidad: Int), librosSinStock(): List<Libro>. Si el libro no existe lanzar NoSuchElementException con mensaje claro; la validación de cantidad la hace el propio Libro.

PRINCIPIOS SOLID:
S: repositorios solo acceden a datos; servicios solo tienen lógica de negocio; nada de UI aquí.
O/L: no modifiques el dominio del commit 1.
I: cada servicio depende solo de las interfaces que usa.
D: los servicios dependen de las interfaces; la única clase que conoce las implementaciones en memoria será, en el commit 3, MainActivity (la raíz de composición).

REGLAS: un archivo por clase, nombres exactamente como están (español), KDoc breve en cada clase, sin librerías externas, sin Room.

NO HAGAS: UI, pantallas, ViewModels, cambios en MainActivity ni en ui.theme, tests, ni cambios en los archivos del commit 1.

AL TERMINAR: verifica que el proyecto compila y dame (1) la lista de archivos creados y (2) un ejemplo de cómo se construiría la cadena de dependencias (autorRepo, categoriaRepo, libroRepo, catalogoService, inventarioService) para usarla en el commit 3.**


**CONTEXTO GENERAL DEL PROYECTO (léelo completo antes de escribir código)

Soy estudiante universitario (UPB, Programación III, docente Johnn Hidalgo) y estoy resolviendo un examen parcial. El caso de estudio elegido es una LIBRERÍA. Debo construir en Kotlin con Jetpack Compose una app Android con: (1) modelo de clases, (2) implementación de las clases, (3) scripts usados, (4) diseño de dos pantallas detallando los componentes de Jetpack Compose, (5) dos pantallas en Jetpack Compose que visualicen información de al menos dos de los objetos implementados, (6) que compile y corra en emulador. Todo se sube a un repo público de GitHub. Este es el TERCER y último prompt/commit; tú empiezas sin memoria, por eso te doy todo el contexto.

PROYECTO BASE:
- Paquete raíz: com.example.primer_parcial
- Kotlin 2.2.10, Jetpack Compose con Material3, minSdk 24. Existe MainActivity.kt con un Greeting de ejemplo y el paquete ui.theme con Primer_parcialTheme (úsalo).
- PROHIBIDO tocar build.gradle.kts, libs.versions.toml o agregar dependencias. Por eso: NO uses Navigation Compose ni ViewModel (no están en el proyecto); NO uses iconos de material-icons-extended (solo Icons.Filled.Home e Icons.Filled.Person, que vienen en el núcleo).

MODELO DE CLASES OFICIAL (YA IMPLEMENTADO en los commits 1 y 2; léelo en el proyecto, NO lo modifiques):
- domain.model: Autor(id, nombre, nacionalidad, biografia; nombreCompleto()), Categoria(id, nombre, descripcion), Editorial(id, nombre, pais), Libro(id, isbn, titulo, autor, categoria, editorial, precio, stock, anioPublicacion; hayStock(), reducirStock(cantidad), aumentarStock(cantidad), precioFinal(politica: PoliticaDescuento)).
- domain.discount: PoliticaDescuento { aplicar(libro: Libro): Double }, SinDescuento, DescuentoPorcentaje(porcentaje: Double), DescuentoPorCategoria(categoria, porcentaje).
- domain.repository: interfaces LibroRepository, AutorRepository, CategoriaRepository.
- data: LibroRepositoryEnMemoria(autorRepository, categoriaRepository), AutorRepositoryEnMemoria, CategoriaRepositoryEnMemoria.
- domain.service: CatalogoService(libroRepository, autorRepository, categoriaRepository) con listarLibros(), listarAutores(), listarCategorias(), librosPorCategoria(idCategoria); InventarioService(libroRepository).

TAREA DE ESTE COMMIT

A) Raíz de composición en MainActivity (reemplaza el Greeting y su preview): crea los repositorios en memoria, luego CatalogoService e InventarioService, y pasa CatalogoService a la UI. MainActivity es la ÚNICA clase que conoce las implementaciones concretas (principio D de SOLID). Mantén enableEdgeToEdge y Primer_parcialTheme.

B) Paquete ui con esta estructura:
- ui/LibreriaApp.kt: composable raíz con Scaffold, CenterAlignedTopAppBar (usa @OptIn(ExperimentalMaterial3Api::class) si hace falta) y NavigationBar con 2 NavigationBarItem ("Catálogo" con Icons.Filled.Home y "Autores" con Icons.Filled.Person). Navegación por estado con rememberSaveable (un Int o enum con la pestaña seleccionada). Recibe CatalogoService por parámetro.
- ui/screens/CatalogoScreen.kt (PANTALLA 1, objeto Libro): título "Catálogo de Libros". Arriba una fila horizontal (LazyRow) de FilterChip: "Todas" + una por cada Categoria (de catalogoService.listarCategorias()); al elegir una, la lista se filtra con catalogoService.librosPorCategoria(id); "Todas" usa listarLibros(). Debajo una fila con un Switch "Aplicar 10% de descuento": si está activo se usa DescuentoPorcentaje(10.0), si no SinDescuento (la política se pasa a libro.precioFinal(...)). Debajo una LazyColumn de LibroCard.
- ui/screens/AutoresScreen.kt (PANTALLA 2, objeto Autor): título "Autores". LazyColumn de AutorCard con datos de catalogoService.listarAutores().
- ui/components/LibroCard.kt: Card con titulo (titleMedium), autor.nombreCompleto(), "categoria.nombre · editorial.nombre · anioPublicacion", el precio: si hay descuento mostrar el precio original tachado (TextDecoration.LineThrough) y el precioFinal; formato "Bs 120.00". Mostrar stock: si libro.hayStock() "Stock: N", si no una etiqueta "Agotado" con color de error del tema.
- ui/components/AutorCard.kt: Card con nombreCompleto() (titleMedium), nacionalidad y biografía.
- Incluye @Preview para LibroCard y AutorCard usando objetos de ejemplo creados en el propio preview.

Reglas de UI: estado con remember / mutableStateOf y state hoisting (los componentes de tarjeta reciben datos por parámetro y no conocen servicios); las pantallas reciben CatalogoService por parámetro (no lo instancian); nada de lógica de negocio dentro de los composables más allá de llamar a los servicios y al modelo; usa MaterialTheme.colorScheme y MaterialTheme.typography, sin colores ni tamaños de texto hardcodeados; padding de 16.dp en pantallas y 8.dp de separación entre tarjetas; textos en español.

C) Documento de diseño de interfaz (Checkpoint 4): crea docs/DISENO_INTERFAZ.md en la raíz del repositorio con, para cada una de las 2 pantallas: objetivo, un boceto en ASCII, la lista de componentes de Jetpack Compose utilizados (Scaffold, CenterAlignedTopAppBar, NavigationBar, NavigationBarItem, LazyColumn, LazyRow, Card, FilterChip, Switch, Text, Row, Column, Spacer, Icon, etc.) con una línea sobre para qué se usa cada uno, y de qué objeto del modelo muestra información (Libro y Autor). Incluye al final una sección corta "Relación con el modelo de clases y SOLID".

NO HAGAS: cambios en domain, data ni en servicios; nuevas dependencias; ViewModels; Navigation Compose; tests.

AL TERMINAR: verifica que el proyecto compila y que la app puede ejecutarse en emulador (si no puedes ejecutarla, revisa que no haya errores de compilación y dime exactamente cómo correrla). Dame (1) la lista de archivos creados o modificados y (2) un resumen de cómo se cumple cada principio SOLID en la UI.**




## Checkpoint 4: Diseño de interfaz (25 puntos)

Diseñé la interfaz de usuario estructurando dos pantallas principales y documentando su propósito, bocetos y componentes en [docs/DISENO_INTERFAZ.md](docs/DISENO_INTERFAZ.md). Los componentes de Jetpack Compose utilizados incluyen `Scaffold`, `CenterAlignedTopAppBar`, `NavigationBar`, `LazyColumn`, `LazyRow`, `FilterChip`, `Switch`, `Card`, `Text`, `Row`, `Column`, `Spacer` e `Icon`.

---

## Checkpoint 5: Interfaz en Jetpack Compose (20 puntos)

Implementé la interfaz gráfica usando Jetpack Compose y Material 3 con dos pantallas funcionales ubicadas en `ui/screens/`:
1. **Catálogo (`CatalogoScreen.kt`):** Muestra el listado de libros con soporte para filtrado por categoría mediante un `LazyRow` de `FilterChip`, un interruptor (*Switch*) para aplicar un 10% de descuento y tarjetas de libros (`LibroCard.kt`) que muestran precios, tachado de precio original y estado de stock o agotado.
2. **Autores (`AutoresScreen.kt`):** Muestra el listado completo de autores mediante una lista vertical (`LazyColumn`) compuesta por tarjetas (`AutorCard.kt`) que detallan el nombre completo, nacionalidad y biografía.

---

## Checkpoint 6: Compilación y ejecución (20 puntos)

Para compilar y ejecutar el proyecto en Android Studio:
1. Abrir la carpeta del proyecto en Android Studio.
2. Esperar a que Gradle finalice la sincronización del proyecto.
3. Seleccionar un dispositivo virtual (Emulador Android con API 24 o superior) o un dispositivo físico conectado.
4. Presionar el botón **Run** (`Shift + F10`) para compilar y ejecutar la app.

Capturas de la ejecución: (pendiente de agregar)

---

## Estructura del proyecto

```text
app/
├── src/main/java/com/example/primer_parcial/
│   ├── data/                 # Repositorios en memoria
│   ├── domain/               # Modelos, descuentos y repositorios (interfaces)
│   │   ├── discount/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/          # Servicios de negocio (CatalogoService, InventarioService)
│   ├── ui/                   # Pantallas y componentes de Compose
│   │   ├── components/
│   │   ├── screens/
│   │   └── theme/
│   └── MainActivity.kt       # Raíz de composición
docs/
└── DISENO_INTERFAZ.md        # Documentación de diseño de interfaz
```
