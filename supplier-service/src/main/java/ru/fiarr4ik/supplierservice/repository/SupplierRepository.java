package ru.fiarr4ik.supplierservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fiarr4ik.supplierservice.entity.Supplier;

/**
 * Репозиторий для работы с сущностью {@link Supplier}.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
