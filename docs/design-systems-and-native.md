# Paso 12 — Cambiar de design system (y renderizar en nativo)

Este paso no añade ni cambia **ni una línea de Java**. Es la demostración de la
idea central de Mateu:

> El backend **define** la UI (componentes abstractos); el **renderer** la
> **pinta**. Están desacoplados, así que el mismo backend se ve en cualquier
> design system y hasta en apps nativas — sin tocar el código.

## Por qué funciona: el backend no sabe de HTML ni de Vaadin

Las pantallas de `hello-world` nunca mencionan un componente concreto: declaran
**intención** (`@Stereotype(money)`, `MetricCard`, `@Lookup`, `AppVariant.MENU_ON_LEFT`…).
El backend emite un árbol de componentes **abstracto** (UIDL) por HTTP:

```
POST /mateu/v3/sync/{route}
```

Con la app arrancada (`./mvnw spring-boot:run`) puedes verlo tú mismo — el
dashboard responde con componentes abstractos, no con marcado de un design system:

```bash
curl -s -X POST http://localhost:8080/mateu/v3/sync/home \
  -H 'Content-Type: application/json' -d '{"route":"/home"}' | python3 -m json.tool
# → verás nodos como "MetricCard", "HorizontalLayout"… nada de <vaadin-*> ni HTML
```

Cualquier renderer que entienda ese árbol puede pintarlo a su manera.

## Cambiar el design system web: una sola dependencia

El design system es **una única dependencia Maven**. En este repo está
parametrizada con la propiedad `mateu.designSystem` (en `pom.xml`), por defecto
`vaadin-lit`. Cámbiala y vuelve a arrancar — **sin cambios de Java**:

```xml
<!-- pom.xml -->
<mateu.designSystem>sapui5-lit</mateu.designSystem>
```

| Design system            | `mateu.designSystem` | Aspecto            |
|--------------------------|----------------------|--------------------|
| Vaadin (Lumo) *(default)*| `vaadin-lit`         | Vaadin             |
| Red Hat PatternFly       | `redhat-lit`         | PatternFly         |
| SAP Fiori / UI5          | `sapui5-lit`         | SAP Fiori          |
| Oracle Redwood           | `redwood-oj-lit`     | Oracle Redwood     |
| Salesforce Lightning     | `slds-lit`           | Salesforce (SLDS 2)|

```bash
./mvnw clean spring-boot:run   # arranca con el design system elegido
```

> Las mismas pantallas (CRUD, dashboard, formulario, lookup, tabs) se renderizan
> con la estética del design system elegido. El dominio y los adaptadores de
> `infrastructure/ui` no cambian.

Como cada renderer es un **web component**, además puedes **empotrar** una
pantalla Mateu dentro de una app existente (React, Vue, Angular, HTML plano),
sea cual sea su tecnología.

## Renderizar en **nativo**: mismo backend, cliente nativo

Los renderers no se limitan al navegador. Todos hablan el mismo protocolo
(`POST /mateu/v3/sync/{route}`), así que **el mismo backend sirve web, escritorio
y móvil a la vez**, sin cambios de código.

| Renderer                 | Plataforma                          | Tecnología           |
|--------------------------|-------------------------------------|----------------------|
| Móvil (React Native)     | iOS, Android                        | Expo / React Native  |
| Escritorio (plugin IDE)  | Windows/macOS/Linux (dentro de IntelliJ) | IntelliJ platform / Swing |

La idea: arranca este backend (`./mvnw spring-boot:run`, puerto 8080) y apunta el
renderer nativo a él:

- **Móvil (React Native, en el monorepo de Mateu `frontend/app/react-native`):**
  `npm run web` para un viewport de móvil en el navegador, o `npm start` + Expo Go
  para tu teléfono real. El host del backend se deriva del dev-server; para este
  repo, ajusta el puerto del backend a 8080.
- **Escritorio (plugin de IntelliJ, `frontend/app/intellij-plugin`):**
  `./gradlew runIde` lanza un IDE con el plugin; la app aparece como *tool window*,
  las CRUD en la ventana inferior, los formularios como pestañas de editor.

El CRUD de productos, el dashboard de KPIs, el formulario y el lookup de categoría
que has construido en los pasos 02–11 funcionan **tal cual** en móvil y escritorio.

## Lo que evidencia este paso

- El backend es **independiente del design system**: emite componentes abstractos.
- Cambiar el look completo (Vaadin → SAP Fiori → Salesforce → PatternFly → Oracle)
  es **una línea** en el `pom.xml`.
- El **mismo** backend se renderiza **nativo** (móvil y escritorio) sin cambios.
- Todo lo anterior confirma la tesis de la arquitectura hexagonal del curso: la UI
  es solo un **adaptador de entrada**, y el renderer es intercambiable.

> Nota: los artefactos `*-lit` se publican con la misma versión que `vaadin-lit`
> (`${mateu.version}`). Si tu repositorio Maven local solo tiene `vaadin-lit`,
> ese es el que arranca; los demás se resuelven al cambiarlos si están publicados
> en tu registro/repositorio de Mateu.
