# Funcionalidad 'Eliminar Empleado' - Módulo RRHH

Esta funcionalidad permite eliminar (dar de baja) a un empleado del sistema de gestión veterinaria.

## Funcionalidades implementadas

* Eliminar empleado por ID
* Validación de existencia del empleado
* Feedback al usuario sobre el resultado de la operación
* Manejo de errores y excepciones

## Datos manejados

* ID del empleado a eliminar
* Verificación de existencia en base de datos
* Confirmación de eliminación exitosa

## Archivos modificados

* `RRHHRepository.java`: Método `eliminarPorId(int id)`
* `RRHHController.java`: Método `eliminarRRHH(int id)`
* `RRHHUI.java`: Función `eliminarRRHH(Scanner scanner)`

## Cómo usar

1. Ejecutar la aplicación del módulo RRHH
2. Seleccionar opción "3. Eliminar RRHH"
3. Ingresar el ID del empleado a eliminar
4. Confirmar la eliminación

La funcionalidad retorna un mensaje de éxito o error según el resultado de la operación.

Esta rama contiene la implementación de la funcionalidad de eliminar empleado. 