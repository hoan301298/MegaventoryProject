package com.megaventory.project.controller;

import com.google.gson.JsonObject;
import com.megaventory.project.entity.Client;
import com.megaventory.project.entity.Product;
import com.megaventory.project.entity.ProductRelationship;
import com.megaventory.project.entity.Supplier;
import com.megaventory.project.repository.ProductRepository;
import com.megaventory.project.repository.SupplierRepository;
import com.megaventory.project.service.ClientService;
import com.megaventory.project.service.ProductService;
import com.megaventory.project.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductByID(id));
    }
    @PostMapping("/save-product")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }
    @PutMapping("/{productId}/client/{clientId}")
    public ResponseEntity<?> clientConnection(@PathVariable int productId, @PathVariable int clientId, @RequestBody ProductRelationship productRelationship) {
        return ResponseEntity.ok(productService.clientConnection(productId, clientId, productRelationship));
    }
    @PutMapping("/{productId}/supplier/{supplierId}")
    public ResponseEntity<?> supplierConnection(@PathVariable int productId, @PathVariable int supplierId, @RequestBody ProductRelationship productRelationship) {
        return ResponseEntity.ok(productService.supplierConnection(productId, supplierId, productRelationship));
    }
}
