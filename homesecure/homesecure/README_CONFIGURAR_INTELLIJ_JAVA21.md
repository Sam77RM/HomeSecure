# HomeSecure - User completo (Java 21)

Este proyecto está configurado para compilar con Java 21. El ZIP incluye el código fuente y el Maven Wrapper; no incluye el JDK de Java porque se instala por separado en el equipo.

## Antes de abrirlo
1. Instala un JDK 21 (por ejemplo, Eclipse Temurin 21 u Oracle JDK 21).
2. En IntelliJ IDEA: `File > Project Structure > Project` y selecciona el JDK 21 como `SDK` y nivel de lenguaje `21`.
3. En `File > Settings > Build, Execution, Deployment > Build Tools > Maven > Runner`, selecciona el JDK 21 en `JRE`.
4. Abre el panel Maven y pulsa `Reload All Maven Projects`.

## Compilar en Windows
Abre la terminal de IntelliJ en la carpeta que contiene `pom.xml` y ejecuta:

```powershell
.\mvnw.cmd clean compile
```

El wrapper descargará Maven si hace falta. Se necesita conexión a Internet para descargar Maven y las dependencias la primera vez.

## Ejecutar
Configura los datos de conexión de MySQL en `src/main/resources/application.properties` y ejecuta `HomeSecureApplication` desde IntelliJ.

## Documentación OpenAPI
Cuando la aplicación esté iniciada, prueba `http://localhost:8080/swagger-ui/index.html`.

## Nota importante
El proyecto se ha configurado para Java 21 y el ZIP se ha validado como archivo comprimido. La compilación Maven no se ha podido verificar en este entorno, así que si IntelliJ muestra un error, comparte el texto completo de la pestaña Build. Este módulo contiene User y no incluye Monitoring.

## Corrección del error `EndPosTable` / `ExceptionInInitializerError`

Este proyecto usa Lombok `1.18.48` tanto como dependencia como procesador de anotaciones para reducir problemas de compatibilidad con compiladores Java recientes.

Después de abrir el proyecto en IntelliJ IDEA:
1. En la ventana Maven, pulsa **Reload All Maven Projects**.
2. Usa `Build > Rebuild Project` o ejecuta `mvnw.cmd clean compile` desde la terminal.
3. Para mantener la configuración del proyecto, selecciona JDK 21 en `File > Project Structure > Project` y en `Settings > Build Tools > Maven > Runner > JRE`.
4. Si el error solo aparece al usar `Build Project` de IntelliJ, activa **Delegate IDE build/run actions to Maven** en `Settings > Build Tools > Maven` si tu versión de IntelliJ muestra esa opción.

Nota: el ZIP no puede cambiar por sí mismo el JDK que IntelliJ tiene seleccionado en tu computadora.
