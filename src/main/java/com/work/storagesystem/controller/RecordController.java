package com.work.storagesystem.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.storagesystem.model.Record;
import com.work.storagesystem.service.RecordService;

@RestController
@RequestMapping("/api/records")
@CrossOrigin
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 查詢所有紀錄
     */
    @GetMapping
    public ResponseEntity<Page<Record>> getAllRecords(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String operator,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Record> records = recordService.getAllRecords(productId, operationType, operator, startTime, endTime, page, size);
        return ResponseEntity.ok(records);
    }

}

