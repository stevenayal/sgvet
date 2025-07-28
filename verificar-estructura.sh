#!/bin/bash

# Script de verificación de estructura del repositorio sgVet
# Ejecutar desde la raíz del proyecto

echo "=========================================="
echo "🔍 VERIFICACIÓN DE ESTRUCTURA DEL PROYECTO"
echo "=========================================="

# Verificar que estamos en el directorio correcto
if [ ! -f "Jenkinsfile" ]; then
    echo "❌ ERROR: No se encontró Jenkinsfile. Ejecutar desde la raíz del proyecto."
    exit 1
fi

echo "✅ Jenkinsfile encontrado"

# Verificar que es un repositorio Git
if [ ! -d ".git" ]; then
    echo "❌ ERROR: No se detectó un repositorio Git."
    exit 1
fi

echo "✅ Repositorio Git detectado"

# Módulos esperados
MODULES=("base" "cliente" "proveedor" "mascota" "rrhh")
MISSING_MODULES=()
MISSING_POMS=()
FOUND_MODULES=()

echo ""
echo "📁 VERIFICACIÓN DE MÓDULOS:"
echo "============================"

for module in "${MODULES[@]}"; do
    if [ -d "$module" ]; then
        FOUND_MODULES+=("$module")
        if [ -f "$module/pom.xml" ]; then
            echo "✅ Módulo $module y su pom.xml encontrados"
            
            # Mostrar información del pom.xml
            ARTIFACT_ID=$(grep -o '<artifactId>[^<]*</artifactId>' "$module/pom.xml" | head -1 | sed 's/<artifactId>\(.*\)<\/artifactId>/\1/')
            if [ ! -z "$ARTIFACT_ID" ]; then
                echo "   📦 ArtifactId: $ARTIFACT_ID"
            fi
        else
            MISSING_POMS+=("$module")
            echo "❌ pom.xml no encontrado en módulo $module"
        fi
    else
        MISSING_MODULES+=("$module")
        echo "❌ Módulo $module no encontrado"
    fi
done

echo ""
echo "📊 ESTRUCTURA DEL PROYECTO:"
echo "============================"

# Mostrar todos los pom.xml encontrados
echo "📦 Archivos pom.xml encontrados:"
find . -name "pom.xml" -type f | sort

echo ""
echo "📂 Directorios src encontrados:"
find . -type d -name "src" | head -10

echo ""
echo "📋 CONTENIDO DE MÓDULOS ENCONTRADOS:"
echo "===================================="

for module in "${FOUND_MODULES[@]}"; do
    echo "📁 Módulo $module:"
    ls -la "$module/"
    echo ""
done

echo "🔍 VERIFICACIÓN DE GIT:"
echo "======================="

# Verificar estado de Git
echo "📝 Estado del repositorio:"
git status --porcelain

echo ""
echo "📜 Últimos commits:"
git log --oneline -5

echo ""
echo "🔗 Remotes configurados:"
git remote -v

echo ""
echo "📦 Submódulos (si existen):"
git submodule status || echo "No hay submódulos configurados"

echo ""
echo "📊 RESUMEN DE VALIDACIÓN:"
echo "========================="

echo "Módulos encontrados: ${#FOUND_MODULES[@]}/${#MODULES[@]}"
echo "Módulos faltantes: ${#MISSING_MODULES[@]}"
echo "pom.xml faltantes: ${#MISSING_POMS[@]}"

# Mostrar módulos faltantes
if [ ${#MISSING_MODULES[@]} -gt 0 ]; then
    echo ""
    echo "❌ MÓDULOS FALTANTES:"
    printf '%s\n' "${MISSING_MODULES[@]}"
fi

# Mostrar pom.xml faltantes
if [ ${#MISSING_POMS[@]} -gt 0 ]; then
    echo ""
    echo "❌ POM.XML FALTANTES:"
    printf '%s\n' "${MISSING_POMS[@]}"
fi

# Verificar si hay un pom.xml padre en la raíz
if [ -f "pom.xml" ]; then
    echo ""
    echo "✅ pom.xml padre encontrado en la raíz"
    if grep -q "<modules>" pom.xml; then
        echo "📦 Proyecto multi-módulo detectado"
    else
        echo "📦 Proyecto simple detectado"
    fi
else
    echo ""
    echo "ℹ️ No hay pom.xml en la raíz (proyecto modular independiente)"
fi

echo ""
echo "🎯 RECOMENDACIONES:"
echo "=================="

if [ ${#MISSING_MODULES[@]} -gt 0 ] || [ ${#MISSING_POMS[@]} -gt 0 ]; then
    echo "❌ PROBLEMAS DETECTADOS:"
    
    if [ ${#MISSING_MODULES[@]} -gt 0 ]; then
        echo "   - Agregar módulos faltantes al repositorio:"
        for module in "${MISSING_MODULES[@]}"; do
            echo "     git add $module/"
        done
    fi
    
    if [ ${#MISSING_POMS[@]} -gt 0 ]; then
        echo "   - Verificar que cada módulo tenga su pom.xml"
    fi
    
    echo ""
    echo "   Comandos para corregir:"
    echo "   git add ."
    echo "   git commit -m 'Agregar módulos faltantes'"
    echo "   git push origin main"
    
    exit 1
else
    echo "✅ ESTRUCTURA CORRECTA"
    echo "   - Todos los módulos están presentes"
    echo "   - Todos los pom.xml están presentes"
    echo "   - El repositorio está listo para Jenkins"
fi

echo ""
echo "=========================================="
echo "✅ VERIFICACIÓN COMPLETADA"
echo "==========================================" 