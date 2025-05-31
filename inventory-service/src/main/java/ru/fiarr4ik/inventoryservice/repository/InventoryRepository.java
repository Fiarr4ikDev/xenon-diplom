package ru.fiarr4ik.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fiarr4ik.inventoryservice.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
