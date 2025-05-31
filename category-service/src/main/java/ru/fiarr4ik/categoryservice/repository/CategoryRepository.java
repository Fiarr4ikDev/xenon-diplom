package ru.fiarr4ik.categoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fiarr4ik.categoryservice.entity.Category;

/**
 * Репозиторий для работы с сущностью {@link Category}.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
