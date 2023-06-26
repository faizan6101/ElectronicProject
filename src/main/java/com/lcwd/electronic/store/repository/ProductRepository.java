package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
Page<Product> findByLiveTrue(Pageable pageable);

//Page<Product>findByCategory(Category category);

}
