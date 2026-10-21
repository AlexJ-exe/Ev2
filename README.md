## Versión de Entorno
* **Android Studio** Quail 4 | 2026.1.4
* **JDK:** Java 11
* **Lenguaje:** Java
* **Min SDK:** 27 (Android 8.1)
* **Target SDK:** 37

---
---

## Instrucciones de Configuración e Instalación

### 1. Clonar el repositorio
```bash
git clone <URL_DEL_REPOSITO>
cd Ev2
```

### 2. Configurar Firebase (Cloud Firestore)
1. Crea un proyecto en [Firebase Console](https://console.firebase.google.com/).
2. Registra la aplicación Android con el nombre de paquete `cl.alex.ev2`.
3. Descarga el archivo `google-services.json` y colócarlo dentro del directorio `/app`.
4. En **Firestore Database**, crea la base de datos y define las reglas de seguridad requeridas.

### 3. Configurar Google Maps API Key
1. Genera una API Key en [Google Cloud Console](https://console.cloud.google.com/) habilitando el servicio **Maps SDK for Android**.
2. En la raíz de tu proyecto, abrir o crear el archivo `local.properties` y añadir la siguiente propiedad:
   ```properties
   MAPS_API_KEY=tu_clave_api_de_google_maps_aqui
   ```
---

## Guía de Pruebas

1. **Prueba de Permisos GPS:**
   * Abrir la pantalla "Registrar Incidencia".
   * Presionar el botón "Obtener Ubicación (GPS)".
   * Permitir la solicitud de ubicación. Verificar que la latitud y longitud se autocompleten con las coordenadas reales.
   * Probar denegando el permiso para validar el mensaje de aviso.

2. **Prueba de Registro e Historial:**
   * Llenar el formulario de incidencia y presionar "Guardar Incidencia".
   * Ir al módulo "Ver Listado" para verificar la presencia de la nueva incidencia en la lista cargada desde Firestore.

3. **Prueba de Mapa:**
   * Entrar a "Ver Mapa".
   * Comprobar la aparición de marcadores sobre la ubicación de las incidencias registradas.

4. **Prueba de Sensor Acelerómetro:**
   * En la pantalla principal, agitar o mover el dispositivo.
   * Verificar que los valores X, Y, Z cambien en tiempo real y el estado cambie a "¡Movimiento o agitación detectada!".
