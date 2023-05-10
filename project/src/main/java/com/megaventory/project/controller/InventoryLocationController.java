package com.megaventory.project.controller;

import com.megaventory.project.entity.InventoryLocation;
import com.megaventory.project.service.InventoryLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventoryLocation")
public class InventoryLocationController {

    @Autowired
    private InventoryLocationService inventoryLocationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventoryLocationById(@PathVariable int id) {
        return ResponseEntity.ok(inventoryLocationService.getInventoryLocationByID(id));
    }

    @PostMapping("/save-inventoryLocation")
    public ResponseEntity<?> saveInventoryLocation(@RequestBody InventoryLocation inventoryLocation) {
            return ResponseEntity.ok(inventoryLocationService.saveInventoryLocation(inventoryLocation));
    }
}
