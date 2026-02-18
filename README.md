# SOLID Learning Project 

Proyecto educativo de Spring Boot diseñado para que estudiantes aprendan sobre los principios SOLID a través de análisis de código real que contiene violaciones **intencionales**.

## Objetivo

Este proyecto es un **CRUD de Productos** con problemas deliberados en al menos **dos principios SOLID**, permitiendo a los estudiantes:

1. Identificar violaciones en código real
2. Entender por qué el código es problemático
3. Aprender cómo refactorizar el código correctamente

## Estructura del Proyecto

```
solid1/
├── pom.xml                              # Configuración Maven
├── src/
│   ├── main/
│   │   ├── java/com/solid/learning/
│   │   │   ├── SolidLearningApplication.java    # App principal
│   │   │   ├── model/
│   │   │   │   └── Product.java                 # Entidad JPA
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java       # Spring Data JPA
│   │   │   ├── service/
│   │   │   │   └── ProductService.java          # ⚠️ VIOLACIONES AQUÍ
│   │   │   └── controller/
│   │   │       └── ProductController.java       # REST API
│   │   └── resources/
│   │       └── application.properties           # Configuración
│   └── test/
└── README.md                            # Este archivo
```

## Endpoints REST

### Crear producto
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Laptop",
  "price": 1500.00,
  "quantity": 50,
  "description": "High performance laptop"
}
```

### Obtener todos los productos
```bash
GET http://localhost:8080/api/products
```

### Obtener producto por ID
```bash
GET http://localhost:8080/api/products/1
```

### Actualizar producto
```bash
PUT http://localhost:8080/api/products/1
Content-Type: application/json

{
  "name": "Laptop Pro",
  "price": 2000.00,
  "quantity": 75,
  "description": "Updated description"
}
```

### Eliminar producto
```bash
DELETE http://localhost:8080/api/products/1
```

### Incrementar stock
```bash
POST http://localhost:8080/api/products/1/increase-stock?amount=20
```

### Decrementar stock
```bash
POST http://localhost:8080/api/products/1/decrease-stock?amount=10
```

### Obtener valor total del inventario
```bash
GET http://localhost:8080/api/products/inventory/value
```

---

## Cómo ejecutar

### Requisitos
- Java 17 o superior
- Maven 3.8 o superior

### Compilar
```bash
mvn clean compile
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Consola H2 Database
Accede a: `http://localhost:8080/h2-console`
- URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (dejar vacío)

---

## Tareas para los Estudiantes

### Nivel 1: Análisis
- [ ] Identifica todas las responsabilidades en `ProductService`
- [ ] Explica por qué el método `createProduct()` viola SRP
- [ ] ¿Qué métodos en `ProductService` harían difícil el mantenimiento?

### Nivel 2: Refactorización (SRP)
- [ ] Crea una clase `ProductValidator` que maneje toda la validación
- [ ] Crea una clase `ProductPricingService` para cálculos de descuento
- [ ] Refactoriza `ProductService` para usar estas nuevas clases

### Nivel 3: Refactorización (DIP)
- [ ] Crea una interfaz `AuditLogger`
- [ ] Crea implementaciones `ConsoleAuditLogger` y `FileAuditLogger`
- [ ] Inyecta el `AuditLogger` en `ProductService` en lugar de usar `System.out.println()`

### Nivel 4: Refactorización Completa
- [ ] Refactoriza completamente el proyecto para seguir todos los principios SOLID
- [ ] Crea pruebas unitarias que verifiquen el comportamiento
- [ ] Documenta los cambios realizados

---

## Configuración adicional

### Base de datos
El proyecto usa **H2 Database** en memoria, lo que permite:
- Sin instalación de BD externos
- BD creada automáticamente
- Datos resetean con cada reinicio

### Stack tecnológico
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok** (simplificar entidades)
- **Maven**

---

## Recursos de aprendizaje

- [Principios SOLID en Wikipedia](https://en.wikipedia.org/wiki/SOLID)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Design Patterns in Java](https://refactoring.guru/design-patterns/java)

---

## Licencia

Proyecto educativo de libre uso.

---

**Creado para estudiantes ITM**
