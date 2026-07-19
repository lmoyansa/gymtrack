# Sprint 2 - Implementación y validación del MVP

## Periodo

**Fecha de inicio:** 10/07/2026  
**Fecha de finalización:** 17/07/2026

## Objetivo del sprint

El objetivo del Sprint 2 fue implementar y validar el producto mínimo viable de GymTrack.

Durante el sprint se desarrollaron las funcionalidades definidas en las historias de usuario, se incorporó persistencia local y se ejecutaron las pruebas necesarias para comprobar el funcionamiento del sistema.

## Trabajo realizado

### Modelo de datos y persistencia

- Creación de las clases principales:
  - Usuario.
  - Rutina.
  - Ejercicio.
  - RutinaEjercicio.
  - Entrenamiento.
  - SerieEntrenamiento.
- Configuración de Room.
- Creación de las entidades y los DAO.
- Implementación de la base de datos local.
- Separación de las rutinas y entrenamientos por usuario.
- Comprobación de que cada usuario solo pudiera acceder a sus propios datos.

### Gestión de usuarios

- Implementación del registro de usuarios.
- Implementación del inicio de sesión.
- Implementación del cierre de sesión.
- Protección de las contraseñas mediante hash, salt e iteraciones.
- Conservación de la sesión mediante SharedPreferences.

### Gestión de rutinas y ejercicios

- Implementación de la pantalla principal.
- Implementación del listado de rutinas.
- Creación de rutinas.
- Consulta del detalle de una rutina.
- Edición y eliminación de rutinas.
- Incorporación de ejercicios.
- Edición y eliminación de ejercicios.
- Configuración de series, repeticiones, peso objetivo y tiempo de descanso.

### Registro de entrenamientos

- Inicio de un entrenamiento desde una rutina.
- Registro de las series realizadas.
- Registro del peso y las repeticiones.
- Cálculo de la duración del entrenamiento.
- Finalización y almacenamiento de la sesión.
- Consulta de los entrenamientos desde el historial.

### Cronómetro

- Implementación del temporizador de descanso.
- Valores rápidos de 60, 90 y 120 segundos.
- Posibilidad de iniciar, pausar y reiniciar el temporizador.
- Integración del temporizador dentro del entrenamiento activo sin perder los datos introducidos.

### Pruebas y correcciones

- Ejecución de pruebas unitarias.
- Ejecución de pruebas de integración.
- Ejecución de pruebas de aceptación.
- Ejecución de pruebas instrumentadas cuando fue necesario utilizar Room o componentes de Android.
- Comprobaciones manuales de navegación e interfaz.
- Documentación de los procedimientos y resultados.
- Captura de evidencias.
- Corrección de los errores detectados.

### Control de versiones

- Desarrollo mediante ramas `feature`.
- Integración de los cambios completados en `develop`.
- Preparación de una versión estable del proyecto.

## Incidencias corregidas

Durante el sprint se resolvieron, entre otras, las siguientes incidencias:

- Errores relacionados con identificadores y archivos XML.
- Problemas de compilación de Gradle.
- Falta de actualización del listado después de editar una rutina.
- Fallos de inicio de sesión después de borrar los datos.
- Pérdida del estado del entrenamiento al abrir el temporizador.
- Cálculo incorrecto de la duración del entrenamiento.
- Configuración de las pruebas instrumentadas.
- Incompatibilidad entre Espresso e `InputManager`.

## Entregables generados

- Aplicación Android GymTrack funcional.
- Código fuente completo.
- Registro, inicio y cierre de sesión.
- Gestión de rutinas y ejercicios.
- Registro de entrenamientos.
- Historial de entrenamientos.
- Temporizador de descanso.
- Persistencia local mediante Room.
- Separación de datos por usuario.
- Pruebas unitarias.
- Pruebas de integración.
- Pruebas de aceptación.
- Documento de evidencias de pruebas.
- Repositorio de GitHub actualizado.
- Tablero de Notion actualizado.
- Versión estable del MVP.

## Reuniones relacionadas

- [Reunión 02 - Revisión del Sprint 1 y planificación del Sprint 2](../reuniones/reunion-02.md)
- [Reunión 03 - Revisión final del Sprint 2](../reuniones/reunion-03.md)

## Puntos de historia

El Sprint 2 alcanzó un total de **99 puntos de historia**.

## Estado del sprint

**Sprint 2 finalizado.**

Las ocho historias de usuario previstas para el MVP fueron implementadas y las pruebas finalizaron correctamente.

## Retrospectiva

| Qué fue bien | Qué se puede mejorar | Problemas encontrados | Acciones de cierre y mejoras futuras |
| --- | --- | --- | --- |
| Se implementaron todas las funcionalidades principales previstas. | Distribuir la implementación entre más sprints. | Inicialmente algunos datos se almacenaban únicamente en memoria. | Completar y revisar la documentación final. |
| Se incorporó persistencia local mediante Room. | Definir antes la persistencia y la asociación de los datos con los usuarios. | Durante parte del desarrollo se utilizó un identificador de usuario provisional. | Integrar la versión estable en `main`. |
| El registro, el inicio de sesión y el cierre de sesión funcionaron correctamente. | Realizar y documentar las pruebas de forma más progresiva. | Al abrir el temporizador se perdía inicialmente el estado del entrenamiento activo. | Comprobar que el repositorio queda limpio y actualizado. |
| Cada usuario quedó limitado a sus propias rutinas y entrenamientos. | Registrar el tiempo real dedicado a cada tarea. | El cálculo inicial de la duración del entrenamiento no reflejaba correctamente el tiempo transcurrido. | Documentar las limitaciones del almacenamiento y la autenticación local. |
| El temporizador se integró sin perder la información introducida. | Ejecutar pruebas en diferentes dispositivos y versiones de Android. | La configuración inicial de Espresso no era compatible con el emulador utilizado. | Dejar la sincronización remota como posible evolución futura. |
| Se ejecutaron correctamente las pruebas unitarias, de integración y de aceptación. | Realizar pruebas con usuarios externos. |  | Ejecutar las operaciones de Room fuera del hilo principal en una futura versión. |
| Los errores bloqueantes fueron corregidos antes del cierre. |  |  | Mejorar la arquitectura y ampliar la cobertura automatizada. |

## Conclusión

El Sprint 2 permitió completar el desarrollo funcional de GymTrack. La aplicación permite registrar usuarios, iniciar y cerrar sesión, crear y gestionar rutinas, añadir ejercicios, registrar entrenamientos, utilizar un temporizador y consultar el historial.

La incorporación de Room permitió conservar los datos después de cerrar la aplicación y separar la información perteneciente a cada usuario.

Las pruebas unitarias, de integración y de aceptación permitieron comprobar los principales componentes y validar las ocho historias de usuario. Las incidencias detectadas fueron corregidas antes de considerar finalizado el MVP.

Con este sprint se da por terminado el desarrollo previsto. La autenticación remota, la sincronización entre dispositivos y otras funciones avanzadas quedan planteadas como posibles mejoras futuras.
