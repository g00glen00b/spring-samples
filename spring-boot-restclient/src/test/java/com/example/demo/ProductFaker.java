package com.example.demo;

import com.example.demo.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductFaker {
    public static Product aProductMatchingAGivenJSON() {
        return new Product(
            1,
            "Detonator",
            "A detonator made by the ACME corporation in function of Looney Tunes",
            new BigDecimal("100"),
            new BigDecimal("10"),
            new BigDecimal("4.5"),
            23,
            "ACME",
            "various",
            "https://example.org/thumbnail.jpg",
            List.of(
                "https://example.org/image1.jpg",
                "https://example.org/image2.jpg"
            )
        );
    }

    public static String aProductJson() {
        return """
                {
                  "id": 1,
                  "title": "Detonator",
                  "description": "A detonator made by the ACME corporation in function of Looney Tunes",
                  "price": 100,
                  "discountPercentage": 10,
                  "rating": 4.5,
                  "stock": 23,
                  "brand": "ACME",
                  "category": "various",
                  "thumbnail": "https://example.org/thumbnail.jpg",
                  "images": [
                    "https://example.org/image1.jpg",
                    "https://example.org/image2.jpg"
                  ]
                }
            """;
    }
}
