package com.solid.learning.service;

import com.solid.learning.model.Product;
import com.solid.learning.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private static final String LOG_FILE = "logs.txt";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Crea un producto 
     */
    public Product createProduct(Product product) {
        // Validación
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }

        // Lógica de negocio 
        if (product.getQuantity() > 100) {
            product.setPrice(product.getPrice() * 0.9); // Descuento del 10%
        }

        // Persistencia
        Product savedProduct = productRepository.save(product);

        // Logging manual (SRP: manejo de logs)
        logToConsole("CREATE", savedProduct);

        return savedProduct;
    }

    /**
     * Obtiene todos los productos
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        logToConsole("RETRIEVE", null);
        return products;
    }

    /**
     * Obtiene un producto por ID
     */
    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            logToConsole("GET_BY_ID", product.get());
        }
        return product;
    }

    /**
     * Actualiza un producto
     */
    public Product updateProduct(Long id, Product productDetails) {
        // Obtención
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validación manual
        validateProduct(productDetails);

        // Actualización
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setDescription(productDetails.getDescription());

        // Aplicar descuento 
        if (product.getQuantity() > 100) {
            product.setPrice(product.getPrice() * 0.9);
        }

        // Persistencia
        Product updatedProduct = productRepository.save(product);

        // Logging
        logToConsole("UPDATE", updatedProduct);

        return updatedProduct;
    }

    /**
     * Elimina un producto
     */
    public void deleteProduct(Long id) {
        // Validación manual
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validación de negocio
        if (product.getQuantity() > 0) {
            throw new RuntimeException("Cannot delete product with remaining stock");
        }

        // Persistencia
        productRepository.deleteById(id);

        // Logging
        logToConsole("DELETE", product);
    }

    /**
     * Incrementa el stock de un producto
     */
    public Product increaseStock(Long id, Integer amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Lógica de negocio sin abstracción
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        product.setQuantity(product.getQuantity() + amount);
        Product saved = productRepository.save(product);
        
        logToConsole("INCREASE_STOCK", saved);
        return saved;
    }

    /**
     * Decrementa el stock
     */
    public Product decreaseStock(Long id, Integer amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (product.getQuantity() < amount) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - amount);
        Product saved = productRepository.save(product);

        logToConsole("DECREASE_STOCK", saved);
        return saved;
    }

    /**
     * Calcula el valor total del inventario
     */
    public Double calculateInventoryValue() {
        List<Product> products = productRepository.findAll();
        Double total = 0.0;
        
        for (Product product : products) {
            // Lógica de cálculo acoplada aquí
            total += product.getPrice() * product.getQuantity();
        }

        logToConsole("CALCULATE_INVENTORY", null);
        return total;
    }


    /*
    * Validación manual
    */
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

    /**
     * Logging manual 
     */
    private void logToConsole(String action, Product product) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        String message = String.format("[%s] Action: %s, Product: %s",
                timestamp,
                action,
                product != null ? product.getName() : "N/A");
        
        System.out.println(message);
    }
}
