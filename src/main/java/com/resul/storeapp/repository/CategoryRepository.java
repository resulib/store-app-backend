package com.resul.storeapp.repository;

import com.resul.storeapp.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("select s from CategoryEntity s where s.isActive=true ")
    List<CategoryEntity> findAllAndIsActive(boolean isActive);
}
