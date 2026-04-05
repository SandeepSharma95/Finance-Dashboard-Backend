package com.sandeep.demo.controller;

import com.sandeep.demo.model.FinancialRecord;
import com.sandeep.demo.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/records")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String date) {
        
        
        List<FinancialRecord> records = service.getFilteredRecords(type, category, date);
        return ResponseEntity.ok(records);
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