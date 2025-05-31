package ru.fiarr4ik.partservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fiarr4ik.partservice.entity.Part;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {

    Optional<Part> findPartByPartId(Long partId);

    boolean existsByCategoryId(Long categoryId);

    boolean existsBySupplierId(Long supplierId);

}
