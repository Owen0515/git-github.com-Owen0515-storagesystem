package com.work.storagesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.work.storagesystem.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long>,JpaSpecificationExecutor<Record>{

}
