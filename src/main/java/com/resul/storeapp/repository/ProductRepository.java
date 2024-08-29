package com.resul.storeapp.repository;

import com.resul.storeapp.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p from ProductEntity p where p.category.id =?1")
    List<ProductEntity> findAllByCategoryId(Long category);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<ProductEntity> search(@Param("query") String query);
}
