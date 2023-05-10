package com.megaventory.project.service;

import com.megaventory.project.entity.InventoryLocation;
import com.megaventory.project.entity.Product;
import com.megaventory.project.repository.InventoryLocationRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryLocationService {

    @Autowired
    private InventoryLocationRepository inventoryLocationRepository;
    @Autowired
    private RestTemplate restTemplate;

    private final String APIKEY = "2402b53edeb131a0@m140904";
    private final String apiURL = "https://api.megaventory.com/v2017a/InventoryLocation/InventoryLocationUpdate";

    public List<InventoryLocation> getAllInventoryLocation() {return inventoryLocationRepository.findAll();}
    public InventoryLocation getInventoryLocationByID(int id) {
        InventoryLocation result = null;
        for (InventoryLocation inventoryLocation: getAllInventoryLocation()) {
            if(inventoryLocation.getId().equals(id))
                result = inventoryLocation;
        }
        return result;
    }

    public String saveInventoryLocation (InventoryLocation inventoryLocation) {

        try {
            inventoryLocationRepository.save(inventoryLocation);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> inventoryLocationAdd = new HashMap<>();
            inventoryLocationAdd.put("InventoryLocationName", inventoryLocation.getName());
            inventoryLocationAdd.put("InventoryLocationAbbreviation", inventoryLocation.getAbbreviation());
            inventoryLocationAdd.put("InventoryLocationAddress", inventoryLocation.getAddress());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("APIKEY", APIKEY);
            requestBody.put("mvInventoryLocation", inventoryLocationAdd);
            requestBody.put("mvRecordAction","Insert");
            requestBody.put("mvInsertUpdateDeleteSourceApplication","WooCommerce");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            String responseEntity = restTemplate.postForObject(apiURL, requestEntity, String.class);
            return "Insert Successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
