package com.sandeep.demo.service;

import com.sandeep.demo.model.FinancialRecord;
import com.sandeep.demo.repository.FinancialRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinanceService {

    @Autowired
    private FinancialRecordRepository repository;

    public Map<String, Object> getDashboardSummary() {
        List<FinancialRecord> records = repository.findAll();

        double totalIncome = 0.0;
        double totalExpenses = 0.0;
        Map<String, Double> categoryTotals = new HashMap<>();

        
        for (FinancialRecord r : records) {
            double amount = (r.getAmount() != null) ? r.getAmount() : 0.0;

            // Income aur Expense calculate karne ke liye simple if-else
            if ("INCOME".equalsIgnoreCase(r.getType())) {
                totalIncome += amount;
            } else if ("EXPENSE".equalsIgnoreCase(r.getType())) {
                totalExpenses += amount;
            }

            // Category wise totals manually add karna
            if (r.getCategory() != null) {
                String cat = r.getCategory();
                double currentTotal = categoryTotals.getOrDefault(cat, 0.0);
                categoryTotals.put(cat, currentTotal + amount);
            }
        }

        // Recent Activity ke liye list ko sort karna (simple logic)
        List<FinancialRecord> sortedRecords = new ArrayList<>(records);
        sortedRecords.sort((a, b) -> {
            if (a.getDate() == null || b.getDate() == null) return 0;
            return b.getDate().compareTo(a.getDate()); // Latest first
        });

        // Sirf top 5 records lena
        List<FinancialRecord> recentActivity = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedRecords.size()); i++) {
            recentActivity.add(sortedRecords.get(i));
        }

        // Final Result Map
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpenses);
        summary.put("netBalance", totalIncome - totalExpenses);
        summary.put("categoryWise", categoryTotals);
        summary.put("recentActivity", recentActivity);

        return summary;
    }

    public List<FinancialRecord> getAllRecords() {
        return repository.findAll();
    }

    public FinancialRecord saveRecord(FinancialRecord record) {
        return repository.save(record);
    }

    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }
}