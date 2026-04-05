package com.sandeep.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandeep.demo.model.FinancialRecord;

import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    List<FinancialRecord> findByType(String type);
    List<FinancialRecord> findByCategory(String category);
}