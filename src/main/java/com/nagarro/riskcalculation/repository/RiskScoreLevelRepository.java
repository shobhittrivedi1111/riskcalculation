package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.RiskScoreLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskScoreLevelRepository extends JpaRepository<RiskScoreLevel, Integer> {
    Optional<RiskScoreLevel> findByLevel(String level);
    @Query("SELECT r.level FROM RiskScoreLevel r")
    List<String> findAllRiskLevelNames();
}

