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
            System.out.println("6. Solicitar Vacaciones");
            System.out.println("7. Solicitar Permiso");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-7): ");

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
                    case 6:
                        solicitarVacaciones(scanner);
                        break;
                    case 7:
                        solicitarPermiso(scanner);
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

    private static void solicitarVacaciones(Scanner scanner) {
        System.out.print("Ingrese el ID del RRHH que solicita vacaciones: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Fecha de inicio (yyyy-mm-dd): ");
        String fechaInicio = scanner.nextLine();
        System.out.print("Fecha de fin (yyyy-mm-dd): ");
        String fechaFin = scanner.nextLine();

        boolean exito = rrhhController.solicitarVacaciones(id, fechaInicio, fechaFin);
        if (exito) {
            System.out.println("Vacaciones solicitadas correctamente.");
        } else {
            System.out.println("No se pudo solicitar las vacaciones. Verifique el ID.");
        }
    }

    private static void solicitarPermiso(Scanner scanner) {
        System.out.print("Ingrese el ID del RRHH que solicita permiso: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Motivo del permiso: ");
        String motivo = scanner.nextLine();
        System.out.print("Fecha del permiso (yyyy-mm-dd): ");
        String fecha = scanner.nextLine();

        boolean exito = rrhhController.solicitarPermiso(id, motivo, fecha);
        if (exito) {
            System.out.println("Permiso solicitado correctamente.");
        } else {
            System.out.println("No se pudo solicitar el permiso. Verifique el ID.");
        }
    }
}