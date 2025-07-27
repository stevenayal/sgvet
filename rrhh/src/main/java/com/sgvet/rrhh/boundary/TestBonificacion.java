package com.sgvet.rrhh.boundary;

import java.util.Scanner;

public class TestBonificacion {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE FUNCIONALIDAD CALCULAR BONIFICACIÓN ===");
        calcularBonificacion(new Scanner(System.in));
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