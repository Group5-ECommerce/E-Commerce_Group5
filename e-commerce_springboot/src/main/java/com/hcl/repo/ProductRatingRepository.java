package com.hcl.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hcl.entity.Product;
import com.hcl.entity.ProductRating;
import com.hcl.model.ProductRatingId;

public interface ProductRatingRepository extends JpaRepository<ProductRating, ProductRatingId>{
}


