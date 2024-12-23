package com.work.storagesystem.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.work.storagesystem.model.Product;
import com.work.storagesystem.model.Record;
import com.work.storagesystem.repository.RecordRepository;
import com.work.storagesystem.service.RecordService;

@Service
public class RecordServiceImpl implements RecordService{
	
	@Autowired
    private RecordRepository recordRepository;

    /**
     * 創建紀錄
     */
    public void createRecord(Product product, String operationType, Integer quantity, String operator) {
        Record record = new Record();
        record.setProduct(product);
        record.setOperationType(operationType);
        record.setQuantity(quantity);
        record.setOperator(operator);
        record.setOperationTime(LocalDateTime.now());
        recordRepository.save(record);
    }


    /**
     * 查詢所有紀錄
     */
    public Page<Record> getAllRecords(Long productId,String operationType,String operator,LocalDateTime startTime,LocalDateTime endTime,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Record> spec = Specification.where(null);
        if (productId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("product").get("productId"), productId)
            );
        }
        if (operationType != null && !operationType.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("operationType"), operationType)
            );
        }
        if (operator != null && !operator.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("operator"), "%" + operator + "%")
            );
        }
        if (startTime != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("operationTime"), startTime)
            );
        }
        if (endTime != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("operationTime"), endTime)
            );
        }
        return recordRepository.findAll(spec, pageable);
    }

}
