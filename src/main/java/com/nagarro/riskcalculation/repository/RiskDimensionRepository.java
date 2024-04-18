package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.RiskDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskDimensionRepository extends JpaRepository<RiskDimension, String> {

}
