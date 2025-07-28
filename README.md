# Sistema de Gestión de Veterinaria (sgVet)

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

## 📋 Descripción

Sistema de Gestión de Veterinaria (sgVet) es una aplicación Java modular que implementa funcionalidades completas para la gestión de una clínica veterinaria, incluyendo módulos para clientes, mascotas, proveedores, recursos humanos y base de datos.

## 🏗️ Arquitectura del Proyecto

### Módulos Principales

- **📊 base/** - Módulo base con funcionalidades comunes
- **👥 cliente/** - Gestión de clientes de la veterinaria
- **🐾 mascota/** - Gestión de mascotas y pacientes
- **🏢 proveedor/** - Gestión de proveedores y suministros
- **👨‍💼 rrhh/** - Gestión de recursos humanos y empleados
- **🖥️ baseUI/** - Interfaz de usuario base
- **🗄️ sgvetDB/** - Base de datos del sistema

## 🚀 Pipeline CI/CD

El proyecto incluye un pipeline de CI/CD robusto con Jenkins y SonarQube:

- **Jenkins:** Build automatizado y pruebas
- **SonarQube:** Análisis de calidad de código
- **JaCoCo:** Cobertura de pruebas
- **Maven:** Gestión de dependencias y build

## 📚 Documentación

### Documentación Principal
- **[Módulo RRHH](rrhh/README.md)** - Documentación completa del módulo de recursos humanos
- **[Índice de Documentación](rrhh/documentacion/INDICE_DOCUMENTACION.md)** - Organización completa de toda la documentación

### Configuración y Pipeline
- **[Configuración del Repositorio](rrhh/documentacion/CONFIGURACION_REPOSITORIO_STEVENAYAL.md)** - Configuración específica para stevenayal/sgvet
- **[Pipeline Simplificado](rrhh/documentacion/PIPELINE_SIMPLIFICADO_CONFIGURACION.md)** - Configuración del pipeline CI/CD

### Resultados y Estado
- **[Resultados del Pipeline](rrhh/documentacion/RESULTADOS_PIPELINE.md)** - Resultados detallados de Jenkins y SonarQube
- **[Badges y Estado](rrhh/documentacion/BADGES_ESTADO.md)** - Badges y métricas del proyecto

## 🔧 Tecnologías Utilizadas

- **Java 17** - Lenguaje de programación principal
- **Maven** - Gestión de dependencias y build
- **Apache Derby** - Base de datos embebida
- **JUnit 5** - Framework de pruebas
- **JaCoCo** - Cobertura de código
- **Jenkins** - Pipeline de CI/CD
- **SonarQube** - Análisis de calidad

## 📦 Instalación

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- Git

### Compilación
```bash
# Clonar el repositorio
git clone -b rrhh_tl https://github.com/stevenayal/sgvet.git
cd sgvet

# Compilar todos los módulos
mvn clean compile

# Ejecutar pruebas
mvn test

# Generar reportes
mvn site
```

## 🧪 Pruebas

Cada módulo incluye pruebas unitarias completas:

```bash
# Ejecutar pruebas de un módulo específico
cd rrhh
mvn test

# Ejecutar pruebas con cobertura
mvn jacoco:report
```

## 📊 Estado del Proyecto

### Módulo RRHH (Más Avanzado)
- ✅ **Pipeline CI/CD:** Funcionando
- ✅ **Jenkins:** Build exitoso
- ✅ **SonarQube:** Quality Gate pasado
- ✅ **Cobertura:** 85.2% (≥80%)
- ✅ **Pruebas:** 47/47 (100%)
- ✅ **Issues:** 0

### Otros Módulos
- 🔄 **En desarrollo** - Funcionalidades básicas implementadas
- 📋 **Documentación** - En proceso de actualización

## 🔗 Enlaces Útiles

- **Repositorio:** [https://github.com/stevenayal/sgvet](https://github.com/stevenayal/sgvet)
- **Rama Principal:** `rrhh_tl`
- **Jenkins:** [Pipeline de CI/CD](http://jenkins:8080/job/sgVet-stevenayal-pipeline/)
- **SonarQube:** [Análisis de Calidad](http://sonarqube:9000/dashboard?id=sgVet-rrhh)

## 📁 Estructura del Repositorio

```
sgvet/
├── 📄 Jenkinsfile                    # Pipeline de CI/CD
├── 📄 README.md                      # Este archivo
├── 📄 LICENSE                        # Licencia MIT
├── 
├── 📁 rrhh/                          # Módulo RRHH (más documentado)
│   ├── 📄 README.md                  # Documentación principal
│   ├── 📁 documentacion/             # Documentación completa
│   │   ├── 📄 README.md              # Documentación detallada
│   │   ├── 📄 INDICE_DOCUMENTACION.md # Índice de documentación
│   │   ├── 📄 RESULTADOS_PIPELINE.md # Resultados del pipeline
│   │   ├── 📄 BADGES_ESTADO.md       # Badges y estado
│   │   └── 📄 [otros 15 archivos]    # Configuración, correcciones, etc.
│   ├── 📁 sonarqube/                 # Imágenes del pipeline
│   └── 📁 src/                       # Código fuente
├── 
├── 📁 cliente/                       # Módulo de clientes
├── 📁 mascota/                       # Módulo de mascotas
├── 📁 proveedor/                     # Módulo de proveedores
├── 📁 base/                          # Módulo base
├── 📁 baseUI/                        # Interfaz de usuario
├── 📁 sgvetDB/                       # Base de datos
└── 📁 doc/                           # Documentación general
```

## 👥 Contribución

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

**Estado del Proyecto:** 🔄 **EN DESARROLLO**  
**Módulo Más Avanzado:** RRHH ✅ **PRODUCCIÓN READY**  
**Última Actualización:** Enero 2025  
**Versión:** 1.0.0
