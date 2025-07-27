package com.sgvet.rrhh.boundary;

import java.util.Scanner;

public class RRHHUISimple {

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
            System.out.println("6. Solicitar Vacaciones");
            System.out.println("7. Solicitar Permiso");
            System.out.println("8. Evaluaciones de Desempeño");
            System.out.println("9. Calcular Bonificación");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-9): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        System.out.println("✅ Función: Crear RRHH (simulada)");
                        break;
                    case 2:
                        System.out.println("✅ Función: Listar RRHHs (simulada)");
                        break;
                    case 3:
                        System.out.println("✅ Función: Eliminar RRHH (simulada)");
                        break;
                    case 4:
                        System.out.println("✅ Función: Buscar RRHH (simulada)");
                        break;
                    case 5:
                        System.out.println("✅ Función: Actualizar datos del empleado (simulada)");
                        break;
                    case 6:
                        System.out.println("✅ Función: Solicitar Vacaciones (simulada)");
                        break;
                    case 7:
                        System.out.println("✅ Función: Solicitar Permiso (simulada)");
                        break;
                    case 8:
                        System.out.println("✅ Función: Evaluaciones de Desempeño (simulada)");
                        break;
                    case 9:
                        calcularBonificacion(scanner);
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

    private static void calcularBonificacion(Scanner scanner) {
        System.out.println("\n--- Calcular Bonificación ---");
        
        try {
            System.out.print("Ingrese el salario base: $");
            double salarioBase = scanner.nextDouble();
            
            System.out.print("Ingrese el porcentaje de bonificación (%): ");
            double porcentajeBonificacion = scanner.nextDouble();
            
            // Validaciones
            if (salarioBase <= 0) {
                System.out.println("❌ Error: El salario base debe ser mayor a 0.");
                return;
            }
            
            if (porcentajeBonificacion < 0 || porcentajeBonificacion > 100) {
                System.out.println("❌ Error: El porcentaje de bonificación debe estar entre 0 y 100.");
                return;
            }
            
            // Cálculos
            double montoBonificacion = salarioBase * (porcentajeBonificacion / 100);
            double totalAPagar = salarioBase + montoBonificacion;
            
            // Mostrar resultados
            System.out.println("\n--- Resultados del Cálculo ---");
            System.out.println("Salario Base: $" + String.format("%.2f", salarioBase));
            System.out.println("Porcentaje de Bonificación: " + String.format("%.1f", porcentajeBonificacion) + "%");
            System.out.println("Monto de Bonificación: $" + String.format("%.2f", montoBonificacion));
            System.out.println("Total a Pagar: $" + String.format("%.2f", totalAPagar));
            System.out.println("✅ Cálculo completado exitosamente.");
            
        } catch (Exception e) {
            System.out.println("❌ Error: Por favor ingrese valores numéricos válidos.");
            scanner.nextLine(); // Limpiar buffer
        }
    }
} 