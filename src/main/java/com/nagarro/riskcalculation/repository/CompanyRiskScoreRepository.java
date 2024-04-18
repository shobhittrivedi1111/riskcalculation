package com.nagarro.riskcalculation.repository;


import com.nagarro.riskcalculation.model.CompanyRiskScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public interface CompanyRiskScoreRepository extends JpaRepository<CompanyRiskScore, String> {

}

