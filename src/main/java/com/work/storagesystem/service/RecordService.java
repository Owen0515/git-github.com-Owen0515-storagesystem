package com.work.storagesystem.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.work.storagesystem.model.Product;
import com.work.storagesystem.model.Record;

public interface RecordService {
	
	/**
	 * 新增紀錄
	 */
	void createRecord(Product product, String operationType, Integer quantity, String operator);
	
	/**
	 * 查詢所有紀錄
	 */
	Page<Record> getAllRecords(Long productId,String operationType,String operator,LocalDateTime startTime,LocalDateTime endTime,int page,int size);

}
