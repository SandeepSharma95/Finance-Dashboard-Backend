package com.sandeep.demo.service;

import com.sandeep.demo.model.FinancialRecord;
import com.sandeep.demo.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

            if ("INCOME".equalsIgnoreCase(r.getType())) {
                totalIncome += amount;
            } else if ("EXPENSE".equalsIgnoreCase(r.getType())) {
                totalExpenses += amount;
            }

            if (r.getCategory() != null) {
                String cat = r.getCategory();
                categoryTotals.put(cat, categoryTotals.getOrDefault(cat, 0.0) + amount);
            }
        }

        // Recent Activity: Sorting latest first and picking top 5
        List<FinancialRecord> sortedRecords = new ArrayList<>(records);
        sortedRecords.sort((a, b) -> {
            if (a.getDate() == null || b.getDate() == null) return 0;
            return b.getDate().compareTo(a.getDate());
        });

        List<FinancialRecord> recentActivity = sortedRecords.stream()
                .limit(5)
                .collect(Collectors.toList());

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpenses);
        summary.put("netBalance", totalIncome - totalExpenses);
        summary.put("categoryWise", categoryTotals);
        summary.put("recentActivity", recentActivity);

        return summary;
    }

    public List<FinancialRecord> getFilteredRecords(String type, String category, String date) {
        List<FinancialRecord> allRecords = repository.findAll();

        return allRecords.stream()
            .filter(r -> (type == null || type.isEmpty() || r.getType().equalsIgnoreCase(type)))
            .filter(r -> (category == null || category.isEmpty() || r.getCategory().equalsIgnoreCase(category)))
            .filter(r -> (date == null || date.isEmpty() || r.getDate().toString().equals(date)))
            .collect(Collectors.toList());
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