package com.sandeep.demo.controller;

import com.sandeep.demo.model.FinancialRecord;
import com.sandeep.demo.service.FinanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private FinanceService service;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<?> getSummary() {
        return ResponseEntity.ok(service.getDashboardSummary());
    }

    //  See Analyst aur Admin records 
    @GetMapping("/records")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    
    @PostMapping("/admin/records")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody FinancialRecord record) {
        return ResponseEntity.ok(service.saveRecord(record));
    }

    @DeleteMapping("/admin/records/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
}