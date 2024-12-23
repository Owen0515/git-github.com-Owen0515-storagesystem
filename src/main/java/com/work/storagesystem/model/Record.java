package com.work.storagesystem.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="record_id")
    private Long recordId; 

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="operation_type")
    private String operationType; 

    @Column(name="quantity")
    private Integer quantity; 

    @Column(name = "operation_time")
    private LocalDateTime operationTime = LocalDateTime.now(); 

    @Column(name="operator")
    private String operator; 

}
