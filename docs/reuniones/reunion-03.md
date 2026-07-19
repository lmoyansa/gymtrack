# Reunión 03 - Revisión final del Sprint 2

**Fecha:** Viernes, 17/07/2026  
**Asistentes:** Luis Moyano y Baldomero Imbernón Tudela  
**Sprint:** Cierre del Sprint 2 y del proyecto

## Objetivo de la reunión

Comprobar el funcionamiento completo del MVP, revisar los resultados del Sprint 2 y valorar el cierre del proyecto GymTrack.

## Funcionalidades revisadas

- Registro de usuarios.
- Inicio y cierre de sesión.
- Persistencia de la sesión.
- Creación, edición y eliminación de rutinas.
- Consulta del detalle de las rutinas.
- Creación, edición y eliminación de ejercicios.
- Configuración de series, repeticiones, peso objetivo y descanso.
- Registro de entrenamientos.
- Registro de series completadas.
- Historial de entrenamientos.
- Temporizador de descanso.
- Conservación de los datos mediante Room.
- Separación de la información por usuario.

## Pruebas revisadas

- Pruebas unitarias.
- Pruebas de integración.
- Pruebas de aceptación.
- Pruebas instrumentadas utilizadas para los casos que requerían Room o componentes de Android.
- Comprobaciones manuales de navegación e interfaz.
- Evidencias de ejecución y resultados.

## Incidencias comentadas

Durante el Sprint 2 se habían detectado y corregido problemas relacionados con:

- Identificadores y archivos XML.
- Compilación de Gradle.
- Actualización de listados.
- Inicio de sesión después de borrar los datos.
- Conservación del entrenamiento al abrir el temporizador.
- Cálculo de la duración del entrenamiento.
- Configuración de Espresso.
- Compatibilidad de las pruebas instrumentadas con el emulador.

## Resultado de la revisión

Se comprobó que el usuario podía completar el flujo principal de la aplicación:

1. Crear una cuenta o iniciar sesión.
2. Crear una rutina.
3. Añadir ejercicios.
4. Iniciar un entrenamiento.
5. Registrar las series realizadas.
6. Utilizar el temporizador.
7. Finalizar el entrenamiento.
8. Consultar posteriormente la sesión desde el historial.

Las ocho historias de usuario fueron implementadas y las pruebas finalizaron correctamente.

## Decisiones tomadas

- Considerar finalizado el Sprint 2.
- Considerar completado el MVP de GymTrack.
- Integrar la versión estable en la rama `main`.
- Comprobar que el repositorio no contuviera cambios pendientes.
- Completar la documentación final.
- Incorporar las evidencias de las pruebas.
- No crear nuevos sprints dentro del alcance actual.
- Mantener la autenticación remota, la sincronización y otras mejoras avanzadas como posibles evoluciones futuras.

## Aspectos mejorables

- Distribuir la implementación entre más sprints.
- Realizar las estimaciones antes de comenzar cada iteración.
- Registrar las horas reales.
- Ejecutar las pruebas de forma más progresiva.
- Probar la aplicación en diferentes dispositivos.
- Realizar pruebas con usuarios externos.
- Mejorar la arquitectura y evitar las operaciones de Room en el hilo principal.

## Resultado final

El proyecto se consideró terminado, con el MVP funcional, las pruebas superadas y la documentación preparada para su entrega.

## Documento relacionado

- [Sprint 2 - Implementación y validación del MVP](../sprints/sprint-2.md)
