# Instrucciones de Ejecución - SOLID Learning Project
## Cómo ejecutar el proyecto

### Opción 1: Desde Spring Boot Maven Plugin (Recomendado)

```bash
mvn spring-boot:run
```

Esto iniciará la aplicación en `http://localhost:8080`

### Opción 2: Ejecutar el JAR compilado

```bash
mvn clean package
java -jar target/solid-learning-1.0.0.jar
```

## Endpoints disponibles

Una vez que la aplicación esté corriendo, puedes probar los endpoints:

### Crear un producto
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "price": 1500.00,
    "quantity": 150,
    "description": "Gaming laptop"
  }'
```

### Listar todos los productos
```bash
curl http://localhost:8080/api/products
```

### Obtener un producto específico
```bash
curl http://localhost:8080/api/products/1
```

### Actualizar producto
```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Pro",
    "price": 2000.00,
    "quantity": 100,
    "description": "Updated"
  }'
```

### Incrementar stock
```bash
curl -X POST "http://localhost:8080/api/products/1/increase-stock?amount=50"
```

### Decrementar stock
```bash
curl -X POST "http://localhost:8080/api/products/1/decrease-stock?amount=20"
```

### Ver valor del inventario
```bash
curl http://localhost:8080/api/products/inventory/value
```

### Eliminar producto
```bash
curl -X DELETE http://localhost:8080/api/products/1
```

---

## Base de datos H2

Mientras la aplicación está corriendo, accede a la consola H2:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (dejar vacío)

---

## Solución de problemas

### El puerto 8080 ya está en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### Error de Maven no encontrado
Asegúrate de tener Maven instalado y en la variable PATH del sistema.

### Error de permiso en Windows
Ejecuta PowerShell o CMD como administrador.

---