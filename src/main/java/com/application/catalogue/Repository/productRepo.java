package com.application.catalogue.Repository;

import com.application.catalogue.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, String> {
}
