# MiCarro al Día — Mobile

Scaffolding funcional en **Kotlin + Jetpack Compose** de las pantallas del mockup de Figma para el flujo de autenticación. Este avance cubre únicamente las pantallas indicadas para esta entrega.

## Pantallas incluidas

1. **Inicio de sesión** (`LoginScreen`) — correo, contraseña (con toggle de mostrar/ocultar), estado de error de credenciales activo pero no funcional (sin backend), enlace a "¿Olvidaste tu contraseña?" y botón "Registrar Usuario".
2. **Recuperar contraseña** (`ForgotPasswordScreen`) — formulario para solicitar el correo de recuperación, botón de volver y enlace de soporte.
3. **Confirmación de recuperación** (`ConfirmationScreen`) — pantalla de confirmación tras "enviar" el correo, con botón "Cerrar" que regresa al login.
4. **Menú principal / Dashboard** (`DashboardScreen`) — se muestra tras iniciar sesión. Incluye saludo al usuario con botón "Salir", alerta de obligaciones próximas (SOAT, impuesto), accesos a "Registrar vehículo" y "Nueva obligación", y la tarjeta del vehículo con el estado de SOAT y tecnomecánica. "Ver todos" abre la lista de vehículos. Los datos son de maqueta.
5. **Registrar vehículo** (`RegisterVehicleScreen`) — se abre desde "Registrar vehículo" en el dashboard. Formulario con placa (tag COL), marca y modelo obligatorios con validación, y botón fijo "Guardar vehículo" que abre el modal **"Vehículo creado"**.
6. **Mis vehículos** (`VehicleListScreen`) — se muestra al pulsar "Entendido" en el modal o "Ver todos" en el dashboard. Lista los vehículos registrados con su estado ("Al día" / obligaciones pendientes), e incluye "+ Agregar vehículo" y "Volver al inicio". Los vehículos se guardan en memoria (`data/VehicleRepository.kt`) y se pierden al cerrar la app.

Las pantallas son completamente **navegables** (Navigation Compose) y los componentes interactivos (campos, toggle de contraseña, botones, banner de error) están **activos pero no conectados a un backend real**, tal como pide esta entrega.

## Estructura del proyecto

```
app/src/main/java/com/micarroaldia/app/
├── MainActivity.kt
├── navigation/MiCarroNavGraph.kt      # Rutas: login, forgot_password, confirmation, dashboard, register_vehicle, vehicle_list
├── data/VehicleRepository.kt          # Lista de vehículos en memoria (maqueta)
├── ui/screens/                        # Las 6 pantallas
├── ui/components/                     # Header, campos de formulario, banner de error, botones
└── ui/theme/                          # Colores, tipografía y tema Material 3
```

## Cómo ejecutarlo

1. Abrir la carpeta del proyecto en **Android Studio** (Koala o superior).
2. Dejar que Gradle sincronice (usa Gradle 8.7 + AGP 8.5.2 + Kotlin 2.0.21, Jetpack Compose).
3. Ejecutar en un emulador o dispositivo físico con Android 7.0 (API 24) o superior.

También puede compilarse por línea de comandos:

```bash
./gradlew assembleDebug
```

El APK generado queda en `app/build/outputs/apk/debug/`.
