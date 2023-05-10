package com.megaventory.project.repository;

import com.megaventory.project.entity.InventoryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Integer> {
}