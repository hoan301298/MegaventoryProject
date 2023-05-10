package com.megaventory.project.service;

import com.megaventory.project.entity.Client;
import com.megaventory.project.entity.Product;
import com.megaventory.project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    private final String APIKEY = "2402b53edeb131a0@m140904";
    private final String apiURL = "https://api.megaventory.com/v2017a/SupplierClient/SupplierClientUpdate";
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Client getClientByID(int id) {
        Client result = new Client();
        for (Client client: getAllClient()) {
            if(client.getId().equals(id))
                result = client;
        }
        return result;
    }
    private List<Client> getAllClient() {return clientRepository.findAll();}

    public String saveClient(Client client) {
        try {
            clientRepository.save(client);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> supplierClient = new HashMap<>();
            supplierClient.put("SupplierClientType", "Client");
            supplierClient.put("SupplierClientName", client.getName());
            supplierClient.put("SupplierClientEmail", client.getEmail());
            supplierClient.put("SupplierClientShippingAddress1", client.getShippingAddress());
            supplierClient.put("SupplierClientPhone1", client.getPhone());

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

    public Client findClientByEmail(String clientEmail) {
        Client result = null;
        for (Client client: getAllClient()) {
            if(client.getEmail().equals(clientEmail))
                result = client;
        }
        return result;
    }
}