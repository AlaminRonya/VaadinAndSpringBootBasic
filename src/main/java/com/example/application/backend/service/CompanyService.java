package com.example.application.backend.service;

import com.example.application.backend.entity.Company;
import com.example.application.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        getAllCompany().forEach(company ->
                stats.put(company.getName(), company.getEmployees().size()));
        return stats;
    }
}
