package com.solid.learning.controller;

import com.solid.learning.model.Product;
import com.solid.learning.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestión de productos
 * 
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST /api/products - Crear un nuevo producto
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * GET /api/products - Obtener todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/products/{id} - Obtener producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/products/{id} - Actualizar un producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/products/{id} - Eliminar un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/products/{id}/increase-stock - Incrementar stock
     */
    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<Product> increaseStock(
            @PathVariable Long id,
            @RequestParam Integer amount) {
        try {
            Product product = productService.increaseStock(id, amount);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/products/{id}/decrease-stock - Decrementar stock
     */
    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<Product> decreaseStock(
            @PathVariable Long id,
            @RequestParam Integer amount) {
        try {
            Product product = productService.decreaseStock(id, amount);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * GET /api/products/inventory/value - Obtener valor total del inventario
     */
    @GetMapping("/inventory/value")
    public ResponseEntity<Double> getInventoryValue() {
        Double value = productService.calculateInventoryValue();
        return ResponseEntity.ok(value);
    }
}
