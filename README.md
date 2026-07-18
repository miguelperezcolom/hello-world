# hello-world — Aprende Mateu paso a paso

Repo-tutorial para aprender a usar [**Mateu**](https://mateu.io), el framework low-code de UI para Java, sobre una base con **arquitectura hexagonal y DDD**.

El tutorial es una **progresión lineal de ramas**: cada rama añade un paso encima de la anterior. `main` es el punto de partida (sin Mateu) y cada rama numerada introduce una pieza nueva.

## La gran idea

Mateu trata la **UI como un adaptador de entrada** más (igual que una API REST o un consumidor de eventos): vive en `infrastructure/ui`, inyecta casos de uso y queries, y **nunca toca el dominio**. Escribes pantallas de forma declarativa (POJOs con anotaciones) y Mateu emite un **árbol de componentes abstracto** que un *renderer* pinta.

De ahí salen dos superpoderes que este repo demuestra al final:

- **No necesitas una API solo para tu UI**: la UI llama a los casos de uso directamente. Menos contratos y modelos duplicados.
- **El mismo backend se renderiza en cualquier sitio**: distintos *design systems* web (Vaadin, SAP Fiori, Salesforce, PatternFly, Oracle) y hasta **nativo** (móvil con React Native, escritorio con el plugin de IntelliJ) — **sin cambiar Java**. Ver **[docs/design-systems-and-native.md](docs/design-systems-and-native.md)**.

## Requisitos

- Java 21+
- No hace falta instalar Maven: usa el wrapper (`./mvnw`).

## Índice de pasos

| Rama | Qué añade | Pantalla / ruta | Qué aprendes |
|------|-----------|-----------------|--------------|
| **`main`** | Proyecto base: agregado `Product` (hexagonal + DDD), casos de uso, queries y persistencia en memoria. **Sin Mateu.** | — (aún sin UI) | El dominio limpio sobre el que se monta todo |
| **`01-add-home`** | Mateu en el `pom` (dependencias + annotation processor) y una primera pantalla `@UI`. | `/` → "Hello World!" | Arrancar Mateu y renderizar una pantalla mínima con estado |
| **`02-add-crud`** | Un CRUD de productos como adaptador de entrada: `ProductView` (DTO `Identifiable`), `ProductCrudRepository` (`CrudRepository` que delega en los casos de uso) y `ProductCrud extends AutoCrud`. | `/products` | Generar un CRUD con `AutoCrud` conectado al dominio, sin que este se entere de la UI |
| **`03-convert-to-app-with-menu`** | Convierte lo anterior en una app multipantalla: una clase `App` (shell con `@Menu`), `Home` pasa a `@Route("/home")` y el CRUD entra como opción de menú. | `/` (app con menú), `/home` | Estructurar varias pantallas en una aplicación con navegación |
| **`04-add-validations`** | Validaciones de formulario: constraints de Jakarta Bean Validation (`@NotEmpty`, `@Size`, `@NotNull`, `@Min`) sobre los campos de `ProductView`. | `/` → menú Productos (crear/editar) | Cómo Mateu refleja las constraints en el formulario, dejando las invariantes de negocio en el dominio |
| **`05-add-filters`** | Filtros de búsqueda en el listado: `ProductCrud` pasa a `FilteredAutoCrud` con un formulario de filtros `ProductFilters` (texto sobre nombre/SKU + "solo activos"); el filtrado es una query de aplicación (`SearchProductsQuery`). | `/` → menú Productos (barra de filtros) | Añadir filtros a un listado con `FilteredAutoCrud`, separando el tipo de filtro del de fila |
| **`06-add-actions`** | Acciones de listado: dos botones `@ListToolbarButton` (`activate` / `deactivate`) sobre las filas seleccionadas, que delegan en casos de uso nuevos (`ActivateProductUseCase` / `DeactivateProductUseCase`) y devuelven un `Message`. El campo `active` es `@ReadOnly` en el formulario. | `/` → menú Productos (barra de acciones sobre selección) | Añadir acciones a un CRUD con `@ListToolbarButton`, recibiendo las filas seleccionadas y devolviendo feedback, sin tocar el dominio |
| **`07-add-form`** | Pantalla-formulario hecha a mano (`NewProductForm`): campos + un botón `@Button create()` que llama a `CreateProductUseCase` y devuelve un `Message`. Es `@Service @Scope("prototype")` para inyectar el caso de uso teniendo estado de formulario por-petición. | `/` → menú "New product" | Construir un formulario propio (no CRUD) cuya acción llama a un caso de uso, con inyección de dependencias y estado limpio |
| **`08-add-dashboard`** | La `Home` pasa a ser un dashboard: una fila de `MetricCard` (total / activos / inactivos) construida desde una query nueva `GetProductStatsQuery`. | `/home` | Mostrar KPIs y componentes de display alimentados por el read-side |
| **`09-add-form-layout`** | Organiza el formulario de `ProductView` con `@Tab` / `@Section` y declara la intención de presentación con `@Stereotype` (money, textarea, toggle). | `/` → menú Productos (crear/editar) | Agrupar y dar intención a los campos, dejando que Mateu elija el control por design system |
| **`10-add-lookup`** | Segundo agregado `Category` (puerto, repo en memoria seeded y queries) **con su propio CRUD gestionable** (`CategoryCrud`, en el menú) relacionado con `Product` vía `@Lookup`, con suppliers de opciones/label respaldados por queries. | `/` → menú Categories (gestionar) y Productos (campo Category en el form) | Relacionar agregados con `@Lookup` respaldado por servicios de consulta, sin acoplar la vista al origen de datos |
| **`11-app-shell`** | `@App(AppVariant.MENU_ON_LEFT, themeToggle=true)` + `@Title`/`@Subtitle`; la `Home` entra también al menú. | `/` (menú lateral + claro/oscuro) | Configurar el shell, la navegación y el tema como pura configuración, desacoplado de las pantallas |
| **`12-design-systems-and-native`** | Parametriza el design system web como una sola dependencia (`mateu.designSystem`, default `vaadin-lit`) y añade `docs/design-systems-and-native.md`. **Sin cambios de Java.** | `/` (cualquier design system) | Evidenciar que el mismo backend se renderiza en cualquier design system y en nativo (móvil/escritorio) |

## Cómo recorrer el tutorial

```bash
# Situarte en un paso
git checkout 01-add-home

# Ver exactamente qué introduce un paso respecto al anterior
git diff 01-add-home 02-add-crud
git diff 02-add-crud 03-convert-to-app-with-menu
git diff 03-convert-to-app-with-menu 04-add-validations
git diff 04-add-validations 05-add-filters
git diff 05-add-filters 06-add-actions
git diff 06-add-actions 07-add-form
git diff 07-add-form 08-add-dashboard
git diff 08-add-dashboard 09-add-form-layout
git diff 09-add-form-layout 10-add-lookup
git diff 10-add-lookup 11-app-shell
git diff 11-app-shell 12-design-systems-and-native
```

Cada paso es autocontenido: puedes hacer `checkout` de una rama y ejecutar la app en ese punto.

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

Luego abre el navegador:

- En `01-add-home`: http://localhost:8080/ (pantalla "Hello World!")
- En `02-add-crud`: http://localhost:8080/products (CRUD de productos)
- En `03-convert-to-app-with-menu`: http://localhost:8080/ (app con menú)
- En `04-add-validations`: http://localhost:8080/ → menú Productos → crear/editar (el formulario valida)
- En `05-add-filters`: http://localhost:8080/ → menú Productos (barra de filtros sobre el listado)
- En `06-add-actions`: http://localhost:8080/ → menú Productos → selecciona filas y pulsa "Activate" / "Deactivate"
- En `07-add-form`: http://localhost:8080/ → menú "New product" (formulario hecho a mano)
- En `08-add-dashboard`: http://localhost:8080/ → home con tarjetas de KPIs
- En `09-add-form-layout`: http://localhost:8080/ → menú Productos → crear/editar (form con tabs y secciones)
- En `10-add-lookup`: http://localhost:8080/ → menú Productos → crear/editar (campo Category con búsqueda)
- En `11-app-shell`: http://localhost:8080/ (menú lateral + toggle claro/oscuro)
- En `12-design-systems-and-native`: cambia `mateu.designSystem` en el `pom.xml` y `./mvnw clean spring-boot:run` (ver `docs/design-systems-and-native.md`)

> En `main` la app arranca pero todavía no hay pantallas: la UI aparece a partir de `01-add-home`.

> **Al cambiar de rama**, ejecuta `./mvnw clean spring-boot:run`. El annotation processor de Mateu genera controladores a partir de las anotaciones `@UI`/`@Route`; si quedan clases generadas de otra rama en `target/`, pueden chocar (p. ej. `Ambiguous mapping` en `GET /`). Un `clean` lo evita.

## Arquitectura del proyecto base

El dominio se mantiene **puro** (sin dependencias de framework); Spring y Mateu solo aparecen en `application` e `infrastructure`.

```
io.mateu.helloworld
├── domain
│   ├── DomainException
│   └── aggregates/product
│       ├── Product                       (raíz de agregado)
│       └── valueobjects/ ProductId · Sku · Money
├── application
│   ├── out/          ProductRepository            (puerto de salida)
│   ├── usecases/<uc>/  Command + UseCase.handle()  (casos de uso)
│   └── queries/<q>/   interfaces de consulta
└── infrastructure
    ├── persistence/  adaptadores de los puertos (en memoria)
    ├── queries/      implementaciones de las queries
    └── ui/           adaptadores de entrada de Mateu (a partir de 01-add-home)
```

- El wiring se hace con estereotipos Spring (`@Service`, `@Component`, `@Repository`) y `@RequiredArgsConstructor` de Lombok.
- Las pantallas de Mateu son **adaptadores de entrada** en `infrastructure/ui`: inyectan los casos de uso / queries y nunca tocan el dominio.

## Recursos

- Documentación de Mateu: https://mateu.io
- Manual de usuario (Java): https://mateu.io/java-user-manual/
