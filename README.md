# GymTrack

GymTrack es una aplicación Android para la gestión de rutinas de gimnasio y el seguimiento del progreso del usuario.

El objetivo de la aplicación es permitir al usuario organizar sus rutinas de entrenamiento, añadir ejercicios, registrar pesos, series y repeticiones, consultar entrenamientos anteriores y utilizar un cronómetro de descanso entre series.

## Funcionalidades principales previstas

- Registro e inicio de sesión de usuario.
- Creación de rutinas de entrenamiento.
- Gestión de ejercicios dentro de cada rutina.
- Registro de entrenamientos realizados.
- Registro de peso, series y repeticiones.
- Consulta de historial básico de entrenamientos.
- Cronómetro de descanso entre series.

## Tecnologías utilizadas

- Android Studio
- Java
- GitHub para control de versiones
- Notion para la gestión ágil del proyecto
- Figma para el prototipo inicial de pantallas

## Estructura inicial del proyecto

```text
GymTrack/
│
├── README.md
├── .gitignore
├── docs/
│   ├── propuesta-proyecto.pdf
│   ├── prototipo.pdf
│   └── gestion-repositorio.pdf
│
└── app/
    ├── build.gradle
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/com/example/gymtrack/
        │   │   ├── ui/
        │   │   ├── model/
        │   │   ├── data/
        │   │   ├── repository/
        │   │   └── utils/
        │   │
        │   └── res/
        │       ├── drawable/
        │       ├── layout/
        │       ├── mipmap/
        │       └── values/
        │
        ├── test/
        └── androidTest/
