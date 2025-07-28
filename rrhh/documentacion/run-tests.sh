#!/bin/bash

# Script para ejecutar pruebas unitarias del módulo RRHH
# Uso: ./run-tests.sh [opción]

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para mostrar ayuda
show_help() {
    echo -e "${BLUE}Script de Pruebas Unitarias - Módulo RRHH${NC}"
    echo ""
    echo "Uso: $0 [opción]"
    echo ""
    echo "Opciones:"
    echo "  all         - Ejecutar todas las pruebas (default)"
    echo "  compile     - Solo compilar el proyecto"
    echo "  clean       - Limpiar y compilar"
    echo "  coverage    - Ejecutar pruebas con reporte de cobertura"
    echo "  controller  - Ejecutar solo pruebas de controladores"
    echo "  entity      - Ejecutar solo pruebas de entidades"
    echo "  util        - Ejecutar solo pruebas de utilidades"
    echo "  help        - Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0                    # Ejecutar todas las pruebas"
    echo "  $0 coverage           # Con reporte de cobertura"
    echo "  $0 controller         # Solo controladores"
    echo ""
}

# Función para mostrar banner
show_banner() {
    echo -e "${BLUE}"
    echo "=========================================="
    echo "  PRUEBAS UNITARIAS - MÓDULO RRHH"
    echo "=========================================="
    echo -e "${NC}"
}

# Función para verificar que Maven está instalado
check_maven() {
    if ! command -v mvn &> /dev/null; then
        echo -e "${RED}Error: Maven no está instalado o no está en el PATH${NC}"
        echo "Por favor, instala Maven y asegúrate de que esté disponible en el PATH"
        exit 1
    fi
    
    echo -e "${GREEN}✓ Maven encontrado: $(mvn --version | head -n 1)${NC}"
}

# Función para verificar que Java está instalado
check_java() {
    if ! command -v java &> /dev/null; then
        echo -e "${RED}Error: Java no está instalado o no está en el PATH${NC}"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo -e "${GREEN}✓ Java encontrado: $JAVA_VERSION${NC}"
}

# Función para limpiar
clean_project() {
    echo -e "${YELLOW}Limpiando proyecto...${NC}"
    mvn clean
    echo -e "${GREEN}✓ Proyecto limpiado${NC}"
}

# Función para compilar
compile_project() {
    echo -e "${YELLOW}Compilando proyecto...${NC}"
    mvn compile
    echo -e "${GREEN}✓ Proyecto compilado${NC}"
}

# Función para ejecutar todas las pruebas
run_all_tests() {
    echo -e "${YELLOW}Ejecutando todas las pruebas...${NC}"
    mvn test
    echo -e "${GREEN}✓ Todas las pruebas completadas${NC}"
}

# Función para ejecutar pruebas con cobertura
run_tests_with_coverage() {
    echo -e "${YELLOW}Ejecutando pruebas con reporte de cobertura...${NC}"
    
    # Verificar si JaCoCo está configurado
    if ! grep -q "jacoco-maven-plugin" pom.xml; then
        echo -e "${YELLOW}JaCoCo no está configurado. Agregando configuración...${NC}"
        # Aquí podrías agregar la configuración de JaCoCo al pom.xml
    fi
    
    mvn clean test jacoco:report
    echo -e "${GREEN}✓ Pruebas con cobertura completadas${NC}"
    echo -e "${BLUE}Reporte de cobertura disponible en: target/site/jacoco/index.html${NC}"
}

# Función para ejecutar pruebas de controladores
run_controller_tests() {
    echo -e "${YELLOW}Ejecutando pruebas de controladores...${NC}"
    mvn test -Dtest="*ControllerTest"
    echo -e "${GREEN}✓ Pruebas de controladores completadas${NC}"
}

# Función para ejecutar pruebas de entidades
run_entity_tests() {
    echo -e "${YELLOW}Ejecutando pruebas de entidades...${NC}"
    mvn test -Dtest="*Test" -Dtest="!*ControllerTest" -Dtest="!*ValidadorTest"
    echo -e "${GREEN}✓ Pruebas de entidades completadas${NC}"
}

# Función para ejecutar pruebas de utilidades
run_util_tests() {
    echo -e "${YELLOW}Ejecutando pruebas de utilidades...${NC}"
    mvn test -Dtest="*ValidadorTest"
    echo -e "${GREEN}✓ Pruebas de utilidades completadas${NC}"
}

# Función para mostrar resumen
show_summary() {
    echo ""
    echo -e "${BLUE}=========================================="
    echo "  RESUMEN DE EJECUCIÓN"
    echo "=========================================="
    echo -e "${NC}"
    
    # Contar archivos de prueba
    TEST_FILES=$(find src/test/java -name "*Test.java" | wc -l)
    echo -e "Archivos de prueba encontrados: ${GREEN}$TEST_FILES${NC}"
    
    # Mostrar estructura de pruebas
    echo -e "${YELLOW}Estructura de pruebas:${NC}"
    find src/test/java -name "*Test.java" | sort | sed 's/src\/test\/java\//  /'
    
    echo ""
    echo -e "${BLUE}Para más información, consulta: README_TESTS.md${NC}"
}

# Función principal
main() {
    show_banner
    
    # Verificar dependencias
    check_java
    check_maven
    
    # Procesar argumentos
    case "${1:-all}" in
        "all")
            run_all_tests
            ;;
        "compile")
            compile_project
            ;;
        "clean")
            clean_project
            compile_project
            ;;
        "coverage")
            run_tests_with_coverage
            ;;
        "controller")
            run_controller_tests
            ;;
        "entity")
            run_entity_tests
            ;;
        "util")
            run_util_tests
            ;;
        "help"|"-h"|"--help")
            show_help
            exit 0
            ;;
        *)
            echo -e "${RED}Opción desconocida: $1${NC}"
            echo ""
            show_help
            exit 1
            ;;
    esac
    
    show_summary
}

# Ejecutar función principal
main "$@" 