# Declaración de Uso de Inteligencia Artificial

* **Herramienta Utilizada:** Gemini

---

## Finalidad del Uso de IA
1. Resolución de errores de compilación y sincronización de Gradle.
4. Formateo y presentación limpia de datos.
5. Buenas prácticas de seguridad (protección de credenciales privadas con `.gitignore` y `local.properties`).

---

## Ejemplos de Consultas Realizadas

### 1. Formateo de valores del Acelerómetro
* **Consulta:** ¿Para qué sirve la línea `tvSensorValores.setText(String.format(Locale.getDefault(), "X: %.2f Y: %.2f Z: %.2f", x, y, z));` y cómo formateo los números del acelerómetro?
* **Respuesta:** Explicación de `String.format()` usando `%.2f` para redondear las lecturas brutas de aceleración ($m/s^2$) a 2 decimales limpios, manteniendo el formato de puntuación regional (`Locale.getDefault()`).

### 2. Carga de datos desde la BD al Mapa
* **Consulta:** ¿Cómo puedo cargar incidencias de la base de datos en el mapa y cómo funciona?
* **Respuesta:** Explicación del flujo asíncrono con `db.collection("incidencias").get()`, extracción de latitud/longitud de los documentos y renderizado de pines con `mMap.addMarker(new MarkerOptions().position(...))`.

### 3. Configuración Segura de la Clave API de Google Maps
* **Consulta:** ¿Cómo lo defino en el `local.properties` para no subir la clave secreta al repositorio?
* **Solución:** Configuración de `manifestPlaceholders["MAPS_API_KEY"]` en `build.gradle.kts` para inyectar la clave desde `local.properties` a `AndroidManifest.xml` sin publicar credenciales sensibles en Git.