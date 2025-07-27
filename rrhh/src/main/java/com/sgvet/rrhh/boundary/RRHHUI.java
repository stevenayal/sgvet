package com.sgvet.rrhh.boundary;

import com.sgvet.rrhh.control.RRHHController;
import com.sgvet.rrhh.control.EvaluacionDesempenoController;
import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.entity.EvaluacionDesempeno;

import java.util.List;
import java.util.Scanner;

public class RRHHUI {

    // Constants for field names
    private static final String FIELD_ID = "ID";
    private static final String FIELD_NOMBRE = "Nombre";
    private static final String FIELD_APELLIDO = "Apellido";
    private static final String FIELD_CEDULA = "Cédula";
    private static final String FIELD_TELEFONO = "Teléfono";
    private static final String FIELD_CORREO = "Correo";
    private static final String FIELD_CARGO = "Cargo";
    private static final String FIELD_ESPECIALIDAD = "Especialidad";
    private static final String FIELD_FECHA = "Fecha";
    private static final String FIELD_PROMEDIO = "Promedio";
    private static final String FIELD_OBSERVACIONES = "Observaciones";

    // Constants for menu options
    private static final String MENU_RRHH_TITLE = "--- Menu de RRHHs ---";
    private static final String MENU_CREAR_RRHH = "1. Crear RRHH";
    private static final String MENU_LISTAR_RRHH = "2. Listar RRHHs";
    private static final String MENU_ELIMINAR_RRHH = "3. Eliminar RRHH";
    private static final String MENU_BUSCAR_RRHH = "4. Buscar RRHH";
    private static final String MENU_ACTUALIZAR_EMPLEADO = "5. Actualizar datos del empleado";
    private static final String MENU_SOLICITAR_VACACIONES = "6. Solicitar Vacaciones";
    private static final String MENU_SOLICITAR_PERMISO = "7. Solicitar Permiso";
    private static final String MENU_EVALUACIONES = "8. Evaluaciones de Desempeño";
    private static final String MENU_CALCULAR_BONIFICACION = "9. Calcular Bonificación";
    private static final String MENU_VOLVER = "0. Volver al menu principal";
    private static final String MENU_SELECCIONAR_OPCION = "Seleccione una opcion (0-9): ";
    private static final String MENU_EVALUACIONES_TITLE = "--- Menú Evaluaciones ---";
    private static final String MENU_REGISTRAR_EVALUACION = "1. Registrar evaluación";
    private static final String MENU_LISTAR_EVALUACIONES = "2. Listar evaluaciones por empleado";
    private static final String MENU_VOLVER_EVALUACIONES = "0. Volver";
    private static final String MENU_SELECCIONAR_EVALUACION = "Seleccione una opción: ";
    private static final String MENU_ACTUALIZAR_TITLE = "--- Actualizar Datos del Empleado ---";
    private static final String MENU_CALCULAR_BONIFICACION_TITLE = "--- Calcular Bonificación ---";
    private static final String MENU_RESULTADOS_TITLE = "--- Resultados del Cálculo ---";

    // Constants for messages
    private static final String MSG_VOLVIENDO_MENU = "Volviendo al menu principal...";
    private static final String MSG_OPCION_INVALIDA = "Opcion invalida. Intente de nuevo.";
    private static final String MSG_INGRESE_NUMERO = "Por favor, ingrese un numero valido.";
    private static final String MSG_RRHH_CREADO = "RRHH creado correctamente.";
    private static final String MSG_RRHH_ELIMINADO = "RRHH eliminado correctamente.";
    private static final String MSG_RRHH_NO_ELIMINADO = "No se pudo eliminar el RRHH (puede que no exista o tenga dependencias).";
    private static final String MSG_RRHH_NO_ENCONTRADO = "RRHH no encontrado.";
    private static final String MSG_EMPLEADO_NO_ENCONTRADO = "Error: No se encontró el empleado con ID ";
    private static final String MSG_DATOS_ACTUALES = "Datos actuales del empleado:";
    private static final String MSG_INGRESE_NUEVOS_DATOS = "Ingrese los nuevos datos (deje vacío para mantener el valor actual):";
    private static final String MSG_EMPLEADO_ACTUALIZADO = "Empleado actualizado exitosamente.";
    private static final String MSG_ERROR_ACTUALIZAR = "Error al actualizar el empleado.";
    private static final String MSG_VACACIONES_SOLICITADAS = "Vacaciones solicitadas correctamente.";
    private static final String MSG_VACACIONES_NO_SOLICITADAS = "No se pudo solicitar las vacaciones. Verifique el ID.";
    private static final String MSG_PERMISO_SOLICITADO = "Permiso solicitado correctamente.";
    private static final String MSG_PERMISO_NO_SOLICITADO = "No se pudo solicitar el permiso. Verifique el ID.";
    private static final String MSG_VOLVIENDO_MENU_EVALUACIONES = "Volviendo al menú principal...";
    private static final String MSG_OPCION_INVALIDA_EVALUACIONES = "Opción inválida.";
    private static final String MSG_EVALUACION_REGISTRADA = "✅ Evaluación registrada.";
    private static final String MSG_NO_EVALUACIONES = "No se encontraron evaluaciones.";
    private static final String MSG_ERROR_SALARIO = "❌ Error: El salario base debe ser mayor a 0.";
    private static final String MSG_ERROR_PORCENTAJE = "❌ Error: El porcentaje de bonificación debe estar entre 0 y 100.";
    private static final String MSG_ERROR_NUMEROS = "❌ Error: Por favor ingrese valores numéricos válidos.";
    private static final String MSG_CALCULO_COMPLETADO = "✅ Cálculo completado exitosamente.";

    // Constants for prompts
    private static final String PROMPT_INGRESE_DATOS_RRHH = "Ingrese los datos del RRHH:";
    private static final String PROMPT_ID_ELIMINAR = "Ingrese el ID del RRHH a eliminar: ";
    private static final String PROMPT_ID_BUSCAR = "Ingrese el ID del RRHH a buscar: ";
    private static final String PROMPT_ID_ACTUALIZAR = "Ingrese el ID del empleado a actualizar: ";
    private static final String PROMPT_ID_VACACIONES = "Ingrese el ID del RRHH que solicita vacaciones: ";
    private static final String PROMPT_ID_PERMISO = "Ingrese el ID del RRHH que solicita permiso: ";
    private static final String PROMPT_ID_EMPLEADO = "ID del empleado: ";
    private static final String PROMPT_FECHA_INICIO = "Fecha de inicio (yyyy-mm-dd): ";
    private static final String PROMPT_FECHA_FIN = "Fecha de fin (yyyy-mm-dd): ";
    private static final String PROMPT_MOTIVO_PERMISO = "Motivo del permiso: ";
    private static final String PROMPT_FECHA_PERMISO = "Fecha del permiso (yyyy-mm-dd): ";
    private static final String PROMPT_FECHA_EVALUACION = "Fecha (YYYY-MM-DD): ";
    private static final String PROMPT_PUNTUALIDAD = "Puntualidad (1-10): ";
    private static final String PROMPT_ATENCION = "Atención al cliente (1-10): ";
    private static final String PROMPT_TRABAJO = "Trabajo en equipo (1-10): ";
    private static final String PROMPT_RESPONSABILIDAD = "Responsabilidad (1-10): ";
    private static final String PROMPT_OBSERVACIONES = "Observaciones: ";
    private static final String PROMPT_SALARIO_BASE = "Ingrese el salario base: $";
    private static final String PROMPT_PORCENTAJE_BONIFICACION = "Ingrese el porcentaje de bonificación (%): ";

    // Constants for new field prompts
    private static final String PROMPT_NUEVO_NOMBRE = "Nuevo nombre [";
    private static final String PROMPT_NUEVO_APELLIDO = "Nuevo apellido [";
    private static final String PROMPT_NUEVA_CEDULA = "Nueva cédula [";
    private static final String PROMPT_NUEVO_TELEFONO = "Nuevo teléfono [";
    private static final String PROMPT_NUEVO_CORREO = "Nuevo correo [";
    private static final String PROMPT_NUEVO_CARGO = "Nuevo cargo [";
    private static final String PROMPT_NUEVA_ESPECIALIDAD = "Nueva especialidad [";

    // Constants for display labels
    private static final String LABEL_SALARIO_BASE = "Salario Base: $";
    private static final String LABEL_PORCENTAJE_BONIFICACION = "Porcentaje de Bonificación: ";
    private static final String LABEL_MONTO_BONIFICACION = "Monto de Bonificación: $";
    private static final String LABEL_TOTAL_PAGAR = "Total a Pagar: $";

    // Constants for separators
    private static final String SEPARATOR_LINE = "-----------------------------";
    private static final String SEPARATOR_EVALUACION = "----------------------------------";

    // Constants for format strings
    private static final String FORMAT_DECIMAL_2 = "%.2f";
    private static final String FORMAT_DECIMAL_1 = "%.1f";
    private static final String FORMAT_PERCENTAGE = "%";

    static RRHHController rrhhController = new RRHHController();
    static EvaluacionDesempenoController evaluacionController = new EvaluacionDesempenoController();

    public static void main(String[] args) {
        menuRRHHes();
    }

    public static void menuRRHHes() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n" + MENU_RRHH_TITLE);
            System.out.println(MENU_CREAR_RRHH);
            System.out.println(MENU_LISTAR_RRHH);
            System.out.println(MENU_ELIMINAR_RRHH);
            System.out.println(MENU_BUSCAR_RRHH);
            System.out.println(MENU_ACTUALIZAR_EMPLEADO);
            System.out.println(MENU_SOLICITAR_VACACIONES);
            System.out.println(MENU_SOLICITAR_PERMISO);
            System.out.println(MENU_EVALUACIONES);
            System.out.println(MENU_CALCULAR_BONIFICACION);
            System.out.println(MENU_VOLVER);
            System.out.print(MENU_SELECCIONAR_OPCION);

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        crearRRHH(scanner);
                        break;
                    case 2:
                        listarRRHHs();
                        break;
                    case 3:
                        eliminarRRHH(scanner);
                        break;
                    case 4:
                        buscarRRHH(scanner);
                        break;
                    case 5:
                        actualizarEmpleado(scanner);
                        break;
                    case 6:
                        solicitarVacaciones(scanner);
                        break;
                    case 7:
                        solicitarPermiso(scanner);
                        break;
                    case 8:
                        menuEvaluaciones(scanner);
                        break;
                    case 9:
                        calcularBonificacion(scanner);
                        break;
                    case 0:
                        System.out.println(MSG_VOLVIENDO_MENU);
                        break;
                    default:
                        System.out.println(MSG_OPCION_INVALIDA);
                }
            } else {
                System.out.println(MSG_INGRESE_NUMERO);
                scanner.next(); // Limpiar entrada inválida
            }
        }
    }

    // Métodos de RRHH...

    private static void crearRRHH(Scanner scanner) {
        System.out.println(PROMPT_INGRESE_DATOS_RRHH);
        System.out.print(FIELD_ID + ": ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print(FIELD_NOMBRE + ": ");
        String nombre = scanner.nextLine();
        System.out.print(FIELD_APELLIDO + ": ");
        String apellido = scanner.nextLine();
        System.out.print(FIELD_CEDULA + ": ");
        String cedula = scanner.nextLine();
        System.out.print(FIELD_TELEFONO + ": ");
        String telefono = scanner.nextLine();
        System.out.print(FIELD_CORREO + ": ");
        String correo = scanner.nextLine();
        System.out.print(FIELD_CARGO + ": ");
        String cargo = scanner.nextLine();
        System.out.print(FIELD_ESPECIALIDAD + ": ");
        String especialidad = scanner.nextLine();

        RRHH rrhh = new RRHH(id, nombre, apellido, cedula, telefono, correo, cargo, especialidad);
        rrhhController.crearRRHH(rrhh);
        System.out.println(MSG_RRHH_CREADO);
    }

    private static void listarRRHHs() {
        List<RRHH> listaRRHHes = rrhhController.listarRRHHes();
        for (RRHH rrhh : listaRRHHes) {
            System.out.println(FIELD_ID + ": " + rrhh.getId());
            System.out.println(FIELD_NOMBRE + ": " + rrhh.getNombre());
            System.out.println(FIELD_APELLIDO + ": " + rrhh.getApellido());
            System.out.println(FIELD_CEDULA + ": " + rrhh.getCedula());
            System.out.println(FIELD_TELEFONO + ": " + rrhh.getTelefono());
            System.out.println(FIELD_CORREO + ": " + rrhh.getCorreo());
            System.out.println(FIELD_CARGO + ": " + rrhh.getCargo());
            System.out.println(FIELD_ESPECIALIDAD + ": " + rrhh.getEspecialidad());
            System.out.println(SEPARATOR_LINE);
        }
    }

    private static void eliminarRRHH(Scanner scanner) {
        System.out.print(PROMPT_ID_ELIMINAR);
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean eliminado = rrhhController.eliminarRRHH(id);
        if (eliminado) {
            System.out.println(MSG_RRHH_ELIMINADO);
        } else {
            System.out.println(MSG_RRHH_NO_ELIMINADO);
        }
    }

    private static void buscarRRHH(Scanner scanner) {
        System.out.print(PROMPT_ID_BUSCAR);
        int id = scanner.nextInt();
        scanner.nextLine();
        RRHH rrhh = rrhhController.buscarRRHH(id);
        if (rrhh != null) {
            System.out.println(FIELD_ID + ": " + rrhh.getId());
            System.out.println(FIELD_NOMBRE + ": " + rrhh.getNombre());
            System.out.println(FIELD_APELLIDO + ": " + rrhh.getApellido());
            System.out.println(FIELD_CEDULA + ": " + rrhh.getCedula());
            System.out.println(FIELD_TELEFONO + ": " + rrhh.getTelefono());
            System.out.println(FIELD_CORREO + ": " + rrhh.getCorreo());
            System.out.println(FIELD_CARGO + ": " + rrhh.getCargo());
            System.out.println(FIELD_ESPECIALIDAD + ": " + rrhh.getEspecialidad());
        } else {
            System.out.println(MSG_RRHH_NO_ENCONTRADO);
        }
    }

    private static void actualizarEmpleado(Scanner scanner) {
        System.out.println("\n" + MENU_ACTUALIZAR_TITLE);
        System.out.print(PROMPT_ID_ACTUALIZAR);
        int id = scanner.nextInt();
        scanner.nextLine();
        RRHH empleadoExistente = rrhhController.buscarRRHH(id);
        if (empleadoExistente == null) {
            System.out.println(MSG_EMPLEADO_NO_ENCONTRADO + id);
            return;
        }
        System.out.println("\n" + MSG_DATOS_ACTUALES);
        System.out.println(FIELD_NOMBRE + ": " + empleadoExistente.getNombre());
        System.out.println(FIELD_APELLIDO + ": " + empleadoExistente.getApellido());
        System.out.println(FIELD_CEDULA + ": " + empleadoExistente.getCedula());
        System.out.println(FIELD_TELEFONO + ": " + empleadoExistente.getTelefono());
        System.out.println(FIELD_CORREO + ": " + empleadoExistente.getCorreo());
        System.out.println(FIELD_CARGO + ": " + empleadoExistente.getCargo());
        System.out.println(FIELD_ESPECIALIDAD + ": " + empleadoExistente.getEspecialidad());
        System.out.println("\n" + MSG_INGRESE_NUEVOS_DATOS);

        System.out.print(PROMPT_NUEVO_NOMBRE + empleadoExistente.getNombre() + "]: ");
        String nuevoNombre = scanner.nextLine();
        if (nuevoNombre.trim().isEmpty()) {
            nuevoNombre = empleadoExistente.getNombre();
        }

        System.out.print(PROMPT_NUEVO_APELLIDO + empleadoExistente.getApellido() + "]: ");
        String nuevoApellido = scanner.nextLine();
        if (nuevoApellido.trim().isEmpty()) {
            nuevoApellido = empleadoExistente.getApellido();
        }

        System.out.print(PROMPT_NUEVA_CEDULA + empleadoExistente.getCedula() + "]: ");
        String nuevaCedula = scanner.nextLine();
        if (nuevaCedula.trim().isEmpty()) {
            nuevaCedula = empleadoExistente.getCedula();
        }

        System.out.print(PROMPT_NUEVO_TELEFONO + empleadoExistente.getTelefono() + "]: ");
        String nuevoTelefono = scanner.nextLine();
        if (nuevoTelefono.trim().isEmpty()) {
            nuevoTelefono = empleadoExistente.getTelefono();
        }

        System.out.print(PROMPT_NUEVO_CORREO + empleadoExistente.getCorreo() + "]: ");
        String nuevoCorreo = scanner.nextLine();
        if (nuevoCorreo.trim().isEmpty()) {
            nuevoCorreo = empleadoExistente.getCorreo();
        }

        System.out.print(PROMPT_NUEVO_CARGO + empleadoExistente.getCargo() + "]: ");
        String nuevoCargo = scanner.nextLine();
        if (nuevoCargo.trim().isEmpty()) {
            nuevoCargo = empleadoExistente.getCargo();
        }

        System.out.print(PROMPT_NUEVA_ESPECIALIDAD + empleadoExistente.getEspecialidad() + "]: ");
        String nuevaEspecialidad = scanner.nextLine();
        if (nuevaEspecialidad.trim().isEmpty()) {
            nuevaEspecialidad = empleadoExistente.getEspecialidad();
        }

        RRHH empleadoActualizado = new RRHH(id, nuevoNombre, nuevoApellido, nuevaCedula,
                nuevoTelefono, nuevoCorreo, nuevoCargo, nuevaEspecialidad);

        boolean resultado = rrhhController.actualizarEmpleado(empleadoActualizado);

        if (resultado) {
            System.out.println(MSG_EMPLEADO_ACTUALIZADO);
        } else {
            System.out.println(MSG_ERROR_ACTUALIZAR);
        }
    }

    private static void solicitarVacaciones(Scanner scanner) {
        System.out.print(PROMPT_ID_VACACIONES);
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print(PROMPT_FECHA_INICIO);
        String fechaInicio = scanner.nextLine();
        System.out.print(PROMPT_FECHA_FIN);
        String fechaFin = scanner.nextLine();

        boolean exito = rrhhController.solicitarVacaciones(id, fechaInicio, fechaFin);
        if (exito) {
            System.out.println(MSG_VACACIONES_SOLICITADAS);
        } else {
            System.out.println(MSG_VACACIONES_NO_SOLICITADAS);
        }
    }

    private static void solicitarPermiso(Scanner scanner) {
        System.out.print(PROMPT_ID_PERMISO);
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print(PROMPT_MOTIVO_PERMISO);
        String motivo = scanner.nextLine();
        System.out.print(PROMPT_FECHA_PERMISO);
        String fecha = scanner.nextLine();

        boolean exito = rrhhController.solicitarPermiso(id, motivo, fecha);
        if (exito) {
            System.out.println(MSG_PERMISO_SOLICITADO);
        } else {
            System.out.println(MSG_PERMISO_NO_SOLICITADO);
        }
    }

    // Evaluaciones de desempeño

    private static void menuEvaluaciones(Scanner scanner) {
        int opcionEval = -1;

        while (opcionEval != 0) {
            System.out.println("\n" + MENU_EVALUACIONES_TITLE);
            System.out.println(MENU_REGISTRAR_EVALUACION);
            System.out.println(MENU_LISTAR_EVALUACIONES);
            System.out.println(MENU_VOLVER_EVALUACIONES);
            System.out.print(MENU_SELECCIONAR_EVALUACION);
            opcionEval = scanner.nextInt();
            scanner.nextLine();

            switch (opcionEval) {
                case 1:
                    registrarEvaluacion(scanner);
                    break;
                case 2:
                    listarEvaluaciones(scanner);
                    break;
                case 0:
                    System.out.println(MSG_VOLVIENDO_MENU_EVALUACIONES);
                    break;
                default:
                    System.out.println(MSG_OPCION_INVALIDA_EVALUACIONES);
            }
        }
    }

    private static void registrarEvaluacion(Scanner scanner) {
        System.out.print(PROMPT_ID_EMPLEADO);
        int idEmpleado = scanner.nextInt();
        scanner.nextLine();
        System.out.print(PROMPT_FECHA_EVALUACION);
        String fecha = scanner.nextLine();
        System.out.print(PROMPT_PUNTUALIDAD);
        int puntualidad = scanner.nextInt();
        System.out.print(PROMPT_ATENCION);
        int atencion = scanner.nextInt();
        System.out.print(PROMPT_TRABAJO);
        int trabajo = scanner.nextInt();
        System.out.print(PROMPT_RESPONSABILIDAD);
        int responsabilidad = scanner.nextInt();
        scanner.nextLine();
        System.out.print(PROMPT_OBSERVACIONES);
        String obs = scanner.nextLine();

        EvaluacionDesempeno eval = new EvaluacionDesempeno(
                idEmpleado, fecha, puntualidad, atencion, trabajo, responsabilidad, obs
        );
        evaluacionController.registrarEvaluacion(eval);
        System.out.println(MSG_EVALUACION_REGISTRADA);
    }

    private static void listarEvaluaciones(Scanner scanner) {
        System.out.print(PROMPT_ID_EMPLEADO);
        int idEmpleado = scanner.nextInt();
        scanner.nextLine();
        List<EvaluacionDesempeno> lista = evaluacionController.obtenerPorEmpleado(idEmpleado);

        if (lista.isEmpty()) {
            System.out.println(MSG_NO_EVALUACIONES);
        } else {
            for (EvaluacionDesempeno e : lista) {
                System.out.println(FIELD_FECHA + ": " + e.getFecha());
                System.out.println(FIELD_PROMEDIO + ": " + e.getCalificacionFinal());
                System.out.println(FIELD_OBSERVACIONES + ": " + e.getObservaciones());
                System.out.println(SEPARATOR_EVALUACION);
            }
        }
    }

    private static void calcularBonificacion(Scanner scanner) {
        System.out.println("\n" + MENU_CALCULAR_BONIFICACION_TITLE);
        
        try {
            System.out.print(PROMPT_SALARIO_BASE);
            double salarioBase = scanner.nextDouble();
            
            System.out.print(PROMPT_PORCENTAJE_BONIFICACION);
            double porcentajeBonificacion = scanner.nextDouble();
            
            // Validaciones
            if (salarioBase <= 0) {
                System.out.println(MSG_ERROR_SALARIO);
                return;
            }
            
            if (porcentajeBonificacion < 0 || porcentajeBonificacion > 100) {
                System.out.println(MSG_ERROR_PORCENTAJE);
                return;
            }
            
            // Cálculos
            double montoBonificacion = salarioBase * (porcentajeBonificacion / 100);
            double totalAPagar = salarioBase + montoBonificacion;
            
            // Mostrar resultados
            System.out.println("\n" + MENU_RESULTADOS_TITLE);
            System.out.println(LABEL_SALARIO_BASE + String.format(FORMAT_DECIMAL_2, salarioBase));
            System.out.println(LABEL_PORCENTAJE_BONIFICACION + String.format(FORMAT_DECIMAL_1, porcentajeBonificacion) + FORMAT_PERCENTAGE);
            System.out.println(LABEL_MONTO_BONIFICACION + String.format(FORMAT_DECIMAL_2, montoBonificacion));
            System.out.println(LABEL_TOTAL_PAGAR + String.format(FORMAT_DECIMAL_2, totalAPagar));
            System.out.println(MSG_CALCULO_COMPLETADO);
            
        } catch (Exception e) {
            System.out.println(MSG_ERROR_NUMEROS);
            scanner.nextLine(); // Limpiar buffer
        }
    }
}
