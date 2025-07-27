package com.sgvet.rrhh.boundary;
import com.sgvet.rrhh.control.RRHHController;
import com.sgvet.rrhh.entity.RRHH;

import java.util.List;
import java.util.Scanner;

public class RRHHUI {

    static RRHHController rrhhController = new RRHHController();
    public static void main(String[] args) {
        menuRRHHes();
    }
    
    public static void menuRRHHes() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de RRHHs ---");
            System.out.println("1. Crear RRHH");
            System.out.println("2. Listar RRHHs");
            System.out.println("3. Eliminar RRHH");
            System.out.println("4. Buscar RRHH");
            System.out.println("5. Actualizar datos del empleado");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-5): ");

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
                    case 0:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    default:
                        System.out.println("Opcion invalida. Intente de nuevo.");
                }
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.next(); // Limpiar entrada inválida
            }
        }
        // scanner.close(); // No cerrar aquí si se comparte el scanner globalmente
    }

    private static void crearRRHH(Scanner scanner) {
        System.out.println("Ingrese los datos del RRHH:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        RRHH rrhh = new RRHH(id, nombre, apellido, cedula, telefono, correo, cargo, especialidad);
        rrhhController.crearRRHH(rrhh);
        System.out.println("RRHH creado correctamente.");
    }

    private static void listarRRHHs() {
        List<RRHH> listaRRHHes = rrhhController.listarRRHHes();
        for (RRHH rrhh : listaRRHHes) {
            System.out.println("ID: " + rrhh.getId());
            System.out.println("Nombre: " + rrhh.getNombre());
            System.out.println("Apellido: " + rrhh.getApellido());
            System.out.println("Cédula: " + rrhh.getCedula());
            System.out.println("Teléfono: " + rrhh.getTelefono());
            System.out.println("Correo: " + rrhh.getCorreo());
            System.out.println("Cargo: " + rrhh.getCargo());
            System.out.println("Especialidad: " + rrhh.getEspecialidad());
            System.out.println("-----------------------------");
        }
    }

    private static void eliminarRRHH(Scanner scanner) {
        System.out.print("Ingrese el ID del RRHH a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        rrhhController.eliminarRRHH(id);
        System.out.println("RRHH eliminado correctamente.");
    }

    private static void buscarRRHH(Scanner scanner) {
        System.out.print("Ingrese el ID del RRHH a buscar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        RRHH rrhh = rrhhController.buscarRRHH(id);
        if (rrhh != null) {
            System.out.println("ID: " + rrhh.getId());
            System.out.println("Nombre: " + rrhh.getNombre());
            System.out.println("Apellido: " + rrhh.getApellido());
            System.out.println("Cédula: " + rrhh.getCedula());
            System.out.println("Teléfono: " + rrhh.getTelefono());
            System.out.println("Correo: " + rrhh.getCorreo());
            System.out.println("Cargo: " + rrhh.getCargo());
            System.out.println("Especialidad: " + rrhh.getEspecialidad());
        } else {
            System.out.println("RRHH no encontrado.");
        }
    }
    
    private static void actualizarEmpleado(Scanner scanner) {
        System.out.println("\n--- Actualizar Datos del Empleado ---");
        
        // Solicitar ID del empleado a actualizar
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Buscar el empleado existente
        RRHH empleadoExistente = rrhhController.buscarRRHH(id);
        if (empleadoExistente == null) {
            System.out.println("Error: No se encontró el empleado con ID " + id);
            return;
        }
        
        // Mostrar datos actuales
        System.out.println("\nDatos actuales del empleado:");
        System.out.println("Nombre: " + empleadoExistente.getNombre());
        System.out.println("Apellido: " + empleadoExistente.getApellido());
        System.out.println("Cédula: " + empleadoExistente.getCedula());
        System.out.println("Teléfono: " + empleadoExistente.getTelefono());
        System.out.println("Correo: " + empleadoExistente.getCorreo());
        System.out.println("Cargo: " + empleadoExistente.getCargo());
        System.out.println("Especialidad: " + empleadoExistente.getEspecialidad());
        
        System.out.println("\nIngrese los nuevos datos (deje vacío para mantener el valor actual):");
        
        // Solicitar nuevos datos
        System.out.print("Nuevo nombre [" + empleadoExistente.getNombre() + "]: ");
        String nuevoNombre = scanner.nextLine();
        if (nuevoNombre.trim().isEmpty()) {
            nuevoNombre = empleadoExistente.getNombre();
        }
        
        System.out.print("Nuevo apellido [" + empleadoExistente.getApellido() + "]: ");
        String nuevoApellido = scanner.nextLine();
        if (nuevoApellido.trim().isEmpty()) {
            nuevoApellido = empleadoExistente.getApellido();
        }
        
        System.out.print("Nueva cédula [" + empleadoExistente.getCedula() + "]: ");
        String nuevaCedula = scanner.nextLine();
        if (nuevaCedula.trim().isEmpty()) {
            nuevaCedula = empleadoExistente.getCedula();
        }
        
        System.out.print("Nuevo teléfono [" + empleadoExistente.getTelefono() + "]: ");
        String nuevoTelefono = scanner.nextLine();
        if (nuevoTelefono.trim().isEmpty()) {
            nuevoTelefono = empleadoExistente.getTelefono();
        }
        
        System.out.print("Nuevo correo [" + empleadoExistente.getCorreo() + "]: ");
        String nuevoCorreo = scanner.nextLine();
        if (nuevoCorreo.trim().isEmpty()) {
            nuevoCorreo = empleadoExistente.getCorreo();
        }
        
        System.out.print("Nuevo cargo [" + empleadoExistente.getCargo() + "]: ");
        String nuevoCargo = scanner.nextLine();
        if (nuevoCargo.trim().isEmpty()) {
            nuevoCargo = empleadoExistente.getCargo();
        }
        
        System.out.print("Nueva especialidad [" + empleadoExistente.getEspecialidad() + "]: ");
        String nuevaEspecialidad = scanner.nextLine();
        if (nuevaEspecialidad.trim().isEmpty()) {
            nuevaEspecialidad = empleadoExistente.getEspecialidad();
        }
        
        // Crear objeto con datos actualizados
        RRHH empleadoActualizado = new RRHH(id, nuevoNombre, nuevoApellido, nuevaCedula, 
                                           nuevoTelefono, nuevoCorreo, nuevoCargo, nuevaEspecialidad);
        
        // Intentar actualizar
        boolean resultado = rrhhController.actualizarEmpleado(empleadoActualizado);
        
        if (resultado) {
            System.out.println("Empleado actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el empleado.");
        }
    }
}
