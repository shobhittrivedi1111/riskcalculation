package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.ScoreCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreCapRepository extends JpaRepository<ScoreCap, Integer> {

    ScoreCap findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(String conditionName,Integer conditionValue);
}

