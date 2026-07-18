# hello-world — Aprende Mateu paso a paso

Repo-tutorial para aprender a usar [**Mateu**](https://mateu.io), el framework low-code de UI para Java, sobre una base con **arquitectura hexagonal y DDD**.

El tutorial es una **progresión lineal de ramas**: cada rama añade un paso encima de la anterior. `main` es el punto de partida (sin Mateu) y cada rama numerada introduce una pieza nueva.

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

## Cómo recorrer el tutorial

```bash
# Situarte en un paso
git checkout 01-add-home

# Ver exactamente qué introduce un paso respecto al anterior
git diff 01-add-home 02-add-crud
git diff 02-add-crud 03-convert-to-app-with-menu
git diff 03-convert-to-app-with-menu 04-add-validations
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

> En `main` la app arranca pero todavía no hay pantallas: la UI aparece a partir de `01-add-home`.

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
