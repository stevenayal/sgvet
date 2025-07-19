package com.sgvet.proveedor.boundary;
import com.sgvet.proveedor.control.ProveedorController;
import com.sgvet.proveedor.entity.Proveedor;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Scanner;

public class ProveedorUI {

    static ProveedorController proveedorController = new ProveedorController();
    public static void main(String[] args) {
        menuProveedores();
    }
    
    public static void menuProveedores() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de Proveedors ---");
            System.out.println("1. Crear proveedor");
            System.out.println("2. Listar Proveedors");
            System.out.println("3. Eliminar proveedor");
            System.out.println("4. Buscar proveedor");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-4): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        crearProveedor(scanner);
                        break;
                    case 2:
                        listarProveedors();
                        break;
                    case 3:
                        eliminarProveedor(scanner);
                        break;
                    case 4:
                        buscarProveedor(scanner);
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

    private static void crearProveedor(Scanner scanner) {
        System.out.println("Funcionalidad para crear proveedor (pendiente de implementar).");
        // Aquí iría la lógica para crear un proveedor
    }

    private static void listarProveedors() {
        System.out.println("Funcionalidad para listar Proveedors (pendiente de implementar).");
        List<Proveedor> listaProveedores=  proveedorController.listarProveedores();
        for (Proveedor proveedor: listaProveedores){
            System.out.println("Nombre:" + proveedor.getNombre());
            System.out.println("Telefono:" + proveedor.getTelefono());
            System.out.println("Correo:" + proveedor.getCorreo());
            System.out.println("Razon Social:" + proveedor.getRazonSocial());
            System.out.println("Id:" + proveedor.getId());

        }
        // Aquí iría la lógica para listar Proveedors
    }

    private static void eliminarProveedor(Scanner scanner) {
        System.out.println("Funcionalidad para eliminar proveedor (pendiente de implementar).");
        // Aquí iría la lógica para eliminar un proveedor
    }

    private static void buscarProveedor(Scanner scanner) {
        System.out.println("Funcionalidad para buscar proveedor (pendiente de implementar).");
        // Aquí iría la lógica para buscar un proveedor
    }
}
