package com.megaventory.project.service;

import com.megaventory.project.entity.Client;
import com.megaventory.project.entity.Product;
import com.megaventory.project.entity.ProductRelationship;
import com.megaventory.project.entity.Supplier;
import com.megaventory.project.repository.ClientRepository;
import com.megaventory.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private RestTemplate restTemplate;
    private final String APIKEY = "2402b53edeb131a0@m140904";
    @Autowired
    private ClientRepository clientRepository;

    public Product getProductByID(int id) {
        Product result = null;
        for (Product product : getAllProduct()) {
            if (product.getId().equals(id))
                result = product;
        }
        return result;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public String saveProduct(Product product) {
        try {
            final String apiURL = "https://api.megaventory.com/v2017a/Product/ProductUpdate";
            productRepository.save(product);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> productAdd = new HashMap<>();
            productAdd.put("ProductType", "TimeRestrictedService");
            productAdd.put("ProductSKU", product.getSku());
            productAdd.put("ProductDescription", product.getDescription());
            productAdd.put("ProductSellingPrice", product.getSalesPrice());
            productAdd.put("ProductPurchasePrice", product.getPurchasePrice());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("APIKEY", APIKEY);
            requestBody.put("mvProduct", productAdd);
            requestBody.put("mvRecordAction", "Insert");
            requestBody.put("mvInsertUpdateDeleteSourceApplication", "WooCommerce");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            String responseEntity = restTemplate.postForObject(apiURL, requestEntity, String.class);
            return "Insert Successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Product findProductBySKU(Long productSKU) {
        Product result = null;
        for (Product product: getAllProduct()) {
            if(product.getSku().equals(productSKU))
                result = product;
        }
        return result;
    }

    public String clientConnection(int productId, int clientId, ProductRelationship productRelationship) {
        try {

            final String apiURL = "https://api.megaventory.com/v2017a/ProductClient/ProductClientUpdate";

            Product product = findProductBySKU(productRelationship.getProductSKU());
            Client client = clientService.findClientByEmail(productRelationship.getClientEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> productClient = new HashMap<>();
            productClient.put("ProductId", productId);
            productClient.put("ProductClient", client.getId());
            productClient.put("ProductClientPrice", product.getPurchasePrice());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("APIKEY", APIKEY);
            requestBody.put("mvProductClientUpdate", productClient);
            requestBody.put("mvRecordAction", "Insert");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            String responseEntity = restTemplate.postForObject(apiURL, requestEntity, String.class);

            product.setClient(client);
            productRepository.save(product);
            return "Insert Successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String supplierConnection(int productId, int supplierId, ProductRelationship productRelationship) {
        try {

            final String apiURL = "https://api.megaventory.com/v2017a/ProductSupplier/ProductSupplierUpdate";

            Product product = findProductBySKU(productRelationship.getProductSKU());
            Supplier supplier = supplierService.findSupplierByEmail(productRelationship.getSupplierEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> productSupplier = new HashMap<>();
            productSupplier.put("ProductId", productId);
            productSupplier.put("ProductSupplier", supplier.getId());
            productSupplier.put("ProductSupplierPrice", product.getPurchasePrice());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("APIKEY", APIKEY);
            requestBody.put("mvProductSupplierUpdate", productSupplier);
            requestBody.put("mvRecordAction", "Insert");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            String responseEntity = restTemplate.postForObject(apiURL, requestEntity, String.class);

            product.setSupplier(supplier);
            productRepository.save(product);
            return "Insert Successfully!";
        } catch(Exception e){
            return e.getMessage();
        }
    }
}
