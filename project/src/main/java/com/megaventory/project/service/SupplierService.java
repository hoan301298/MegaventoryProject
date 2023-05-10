package com.megaventory.project.service;

import com.megaventory.project.entity.Supplier;
import com.megaventory.project.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    private final String APIKEY = "2402b53edeb131a0@m140904";
    private final String apiURL = "https://api.megaventory.com/v2017a/SupplierClient/SupplierClientUpdate";
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RestTemplate restTemplate;
    public List<Supplier> getAllSupplier() { return supplierRepository.findAll();}
    public Supplier getSupplierByID(int id) {

        Supplier result = null;
        for (Supplier supplier: getAllSupplier()) {
            if(supplier.getId().equals(id))
                result = supplier;
        }
        return result;
    }

    public String saveSupplier (Supplier supplier) {

        try {
            supplierRepository.save(supplier);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> supplierClient = new HashMap<>();
            supplierClient.put("SupplierClientType", "Supplier");
            supplierClient.put("SupplierClientName", supplier.getName());
            supplierClient.put("SupplierClientEmail", supplier.getEmail());
            supplierClient.put("SupplierClientShippingAddress1", supplier.getShippingAddress());
            supplierClient.put("SupplierClientPhone1", supplier.getPhone());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("APIKEY", APIKEY);
            requestBody.put("mvSupplierClient", supplierClient);
            requestBody.put("mvGrantPermissionsToAllUser","True");
            requestBody.put("mvRecordAction","Insert");
            requestBody.put("mvInsertUpdateDeleteSourceApplication","Magento");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            String responseEntity = restTemplate.postForObject(apiURL, requestEntity, String.class);
            return "Insert Successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Supplier findSupplierByEmail(String supplierEmail) {
        Supplier result = null;
        for (Supplier supplier : getAllSupplier()){
            if(supplier.getEmail().equals(supplierEmail))
                result = supplier;
        }
        return result;
    }
}
