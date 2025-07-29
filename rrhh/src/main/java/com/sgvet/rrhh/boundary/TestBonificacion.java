package com.sgvet.rrhh.boundary;

import java.util.Scanner;

public class TestBonificacion {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE FUNCIONALIDAD: CALCULAR BONIFICACIÓN ===");
        calcularBonificacion(new Scanner(System.in));
    }
    
    private static void calcularBonificacion(Scanner scanner) {
        imprimirTitulo("Calcular Bonificación");
        
        try {
            double salarioBase = leerSalarioBase(scanner);
            double porcentajeBonificacion = leerPorcentajeBonificacion(scanner);
            
            // Validaciones
            if (!validarSalarioBase(salarioBase)) {
                return;
            }
            
            if (!validarPorcentajeBonificacion(porcentajeBonificacion)) {
                return;
            }
            
            // Cálculos
            double montoBonificacion = calcularMontoBonificacion(salarioBase, porcentajeBonificacion);
            double totalAPagar = calcularTotalAPagar(salarioBase, montoBonificacion);
            
            // Mostrar resultados
            mostrarResultados(salarioBase, porcentajeBonificacion, montoBonificacion, totalAPagar);
            
        } catch (Exception e) {
            manejarErrorEntrada(scanner);
        }
    }
    
    /**
     * Lee el salario base desde el scanner
     */
    private static double leerSalarioBase(Scanner scanner) {
        System.out.print("Ingrese el salario base: $");
        return scanner.nextDouble();
    }
    
    /**
     * Lee el porcentaje de bonificación desde el scanner
     */
    private static double leerPorcentajeBonificacion(Scanner scanner) {
        System.out.print("Ingrese el porcentaje de bonificación (%): ");
        return scanner.nextDouble();
    }
    
    /**
     * Valida que el salario base sea mayor a 0
     */
    private static boolean validarSalarioBase(double salarioBase) {
        if (salarioBase <= 0) {
            imprimirError("El salario base debe ser mayor a 0.");
            return false;
        }
        return true;
    }
    
    /**
     * Valida que el porcentaje de bonificación esté entre 0 y 100
     */
    private static boolean validarPorcentajeBonificacion(double porcentajeBonificacion) {
        if (porcentajeBonificacion < 0 || porcentajeBonificacion > 100) {
            imprimirError("El porcentaje de bonificación debe estar entre 0 y 100.");
            return false;
        }
        return true;
    }
    
    /**
     * Calcula el monto de bonificación
     */
    private static double calcularMontoBonificacion(double salarioBase, double porcentajeBonificacion) {
        return salarioBase * (porcentajeBonificacion / 100);
    }
    
    /**
     * Calcula el total a pagar
     */
    private static double calcularTotalAPagar(double salarioBase, double montoBonificacion) {
        return salarioBase + montoBonificacion;
    }
    
    /**
     * Muestra los resultados del cálculo
     */
    private static void mostrarResultados(double salarioBase, double porcentajeBonificacion, 
                                        double montoBonificacion, double totalAPagar) {
        imprimirTitulo("Resultados del Cálculo");
        imprimirValor("Salario Base", formatearMoneda(salarioBase));
        imprimirValor("Porcentaje de Bonificación", formatearPorcentaje(porcentajeBonificacion));
        imprimirValor("Monto de Bonificación", formatearMoneda(montoBonificacion));
        imprimirValor("Total a Pagar", formatearMoneda(totalAPagar));
        imprimirExito("Cálculo completado exitosamente.");
    }
    
    /**
     * Imprime un título con formato
     */
    private static void imprimirTitulo(String titulo) {
        System.out.println("\n--- " + titulo + " ---");
    }
    
    /**
     * Imprime un valor con etiqueta
     */
    private static void imprimirValor(String etiqueta, String valor) {
        System.out.println(etiqueta + ": " + valor);
    }
    
    /**
     * Imprime un mensaje de error
     */
    private static void imprimirError(String mensaje) {
        System.out.println("❌ Error: " + mensaje);
    }
    
    /**
     * Imprime un mensaje de éxito
     */
    private static void imprimirExito(String mensaje) {
        System.out.println("✅ " + mensaje);
    }
    
    /**
     * Formatea un valor como moneda
     */
    private static String formatearMoneda(double valor) {
        return "$" + String.format("%.2f", valor);
    }
    
    /**
     * Formatea un valor como porcentaje
     */
    private static String formatearPorcentaje(double valor) {
        return String.format("%.1f", valor) + "%";
    }
    
    /**
     * Maneja errores de entrada inválida
     */
    private static void manejarErrorEntrada(Scanner scanner) {
        imprimirError("Por favor ingrese valores numéricos válidos.");
        scanner.nextLine(); // Limpiar buffer
    }
} 