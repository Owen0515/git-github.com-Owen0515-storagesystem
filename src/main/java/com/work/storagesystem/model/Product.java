package com.work.storagesystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@DynamicInsert
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long productId; 

    @Column(name="product_name")
    private String productName; 

    @Column(name="product_description")
    private String productDescription; 

    @Column(name="price")
    private BigDecimal price; 

    @Column(name="quantity")
    private Integer quantity; 

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name="is_deleted")
    private Boolean deleted;

}
