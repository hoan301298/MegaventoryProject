package com.megaventory.project.controller;

import com.megaventory.project.entity.Client;
import com.megaventory.project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable int id) {
        return ResponseEntity.ok(clientService.getClientByID(id));
    }

    @PostMapping("/save-client")
    public ResponseEntity<?> saveClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.saveClient(client));
    }
}
