package com.example.demo.model;

import java.math.BigDecimal;
import java.util.List;

public record Product(
    int id,
    String title,
    String description,
    BigDecimal price,
    BigDecimal discountPercentage,
    BigDecimal rating,
    int stock,
    String brand,
    String category,
    String thumbnail,
    List<String> images) {
}
