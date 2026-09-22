# MiCarro al Día — Mobile

Scaffolding funcional en **Kotlin + Jetpack Compose** de las pantallas del mockup de Figma para el flujo de autenticación. Este avance cubre únicamente las pantallas indicadas para esta entrega.

## Pantallas incluidas

1. **Inicio de sesión** (`LoginScreen`) — correo, contraseña (con toggle de mostrar/ocultar), estado de error de credenciales activo pero no funcional (sin backend), enlace a "¿Olvidaste tu contraseña?" y botón "Registrar Usuario".
2. **Recuperar contraseña** (`ForgotPasswordScreen`) — formulario para solicitar el correo de recuperación, botón de volver y enlace de soporte.
3. **Confirmación de recuperación** (`ConfirmationScreen`) — pantalla de confirmación tras "enviar" el correo, con botón "Cerrar" que regresa al login.

Las pantallas son completamente **navegables** (Navigation Compose) y los componentes interactivos (campos, toggle de contraseña, botones, banner de error) están **activos pero no conectados a un backend real**, tal como pide esta entrega.

## Estructura del proyecto

```
app/src/main/java/com/micarroaldia/app/
├── MainActivity.kt
├── navigation/MiCarroNavGraph.kt      # Rutas: login, forgot_password, confirmation
├── ui/screens/                        # Las 3 pantallas
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
