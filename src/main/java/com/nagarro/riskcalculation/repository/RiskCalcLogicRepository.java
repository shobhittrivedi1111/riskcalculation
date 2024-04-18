package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.RiskCalcLogic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskCalcLogicRepository extends JpaRepository<RiskCalcLogic, Integer> {
    Optional<RiskCalcLogic> findByElementName(String elementName); // Use 'findByElement_name' instead of 'findByElement_Name'
}
