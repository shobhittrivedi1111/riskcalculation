package com.nagarro.riskcalculation.service;

import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import com.nagarro.riskcalculation.repository.ScoreCapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Slf4j
@Service
public class ScoreCapService {
    @Autowired
    private ScoreCapRepository scoreCapRepository;
    @Autowired
    private RiskScoreLevelRepository riskScoreLevelRepository;

    public List<ScoreCap> getAllScoreCaps() {
        log.info("Getting all Score Caps");
        return scoreCapRepository.findAll();
    }

    public ScoreCap getScoreCapById(int id) {
        log.info("Getting Score Cap by ID: {}", id);
        return scoreCapRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("ScoreCap with id" + id + "not found"));
    }

    public ScoreCap createScoreCap(ScoreCap scoreCap) {
        log.info("Creating Score Cap: {}", scoreCap);
        Optional<ScoreCap> scoreCap1=scoreCapRepository.findById(scoreCap.getId());
        if(scoreCap1.isPresent()){
            throw new DataAlreadyExistsException("Score Cap Already Present!");
        }
        Optional<ScoreCap> existingScoreCap = Optional.ofNullable(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(scoreCap.getRiskScoreLevel().getLevel(), scoreCap.getConditionValue()));
        if (existingScoreCap.isPresent()) {
            log.info("ScoreCap already exists for ID: {}", scoreCap.getId());
            throw new DataAlreadyExistsException("Score Cap Already Present!");// Return the existing ScoreCap
        }
        Optional<RiskScoreLevel> existingScoreLevel = riskScoreLevelRepository.findByLevel(scoreCap.getRiskScoreLevel().getLevel());
        if (existingScoreLevel.isPresent()) {
            scoreCap.setRiskScoreLevel(existingScoreLevel.get());
            return scoreCapRepository.save(scoreCap);
        }
            else {
            throw new DataAlreadyExistsException("Score Cap Not Present is RiskScore Level!");
        }
    }

public ScoreCap updateScoreCap(int id, ScoreCap scoreCap) {
    log.info("Updating Score Cap with ID: {}", id);

    ScoreCap existingScoreCap = scoreCapRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("ScoreCap with name" + id + "not found"));

    // Check if a ScoreCap with the same RiskScoreLevel and conditionValue already exists
    Optional<ScoreCap> existingScoreCapWithSameLevelAndConditionValue = Optional.ofNullable(scoreCapRepository.findByRiskScoreLevel_LevelIgnoreCaseAndConditionValue(scoreCap.getRiskScoreLevel().getLevel(), scoreCap.getConditionValue()));
    if (existingScoreCapWithSameLevelAndConditionValue.isPresent() && existingScoreCapWithSameLevelAndConditionValue.get().getId() != id) {
        log.info("ScoreCap already exists for ID: {}", scoreCap.getId());
        throw new DataAlreadyExistsException("Score Cap with Same Condition Name and Level Already Exists!");// Return the existing ScoreCap
    }

    // Check if the RiskScoreLevel referenced by the scoreCap parameter exists
    Optional<RiskScoreLevel> existingScoreLevel = riskScoreLevelRepository.findByLevel(scoreCap.getRiskScoreLevel().getLevel());
    if (existingScoreLevel.isPresent()) {
        existingScoreCap.setTotal_risk_capped_score(scoreCap.getTotal_risk_capped_score());
        existingScoreCap.setRiskScoreLevel(existingScoreLevel.get());
        existingScoreCap.setConditionValue(scoreCap.getConditionValue());
    } else {
        // Save the new RiskScoreLevel first
        RiskScoreLevel newScoreLevel = new RiskScoreLevel();
        newScoreLevel.setLevel(scoreCap.getRiskScoreLevel().getLevel());
        newScoreLevel.setMinScore(scoreCap.getRiskScoreLevel().getMinScore());
        newScoreLevel.setMaxScore(scoreCap.getRiskScoreLevel().getMaxScore());

        RiskScoreLevel savedScoreLevel = riskScoreLevelRepository.save(newScoreLevel);
        existingScoreCap.setRiskScoreLevel(savedScoreLevel);
    }

    return scoreCapRepository.save(existingScoreCap);
}


    public boolean deleteScoreCap(int id) {
        log.info("Deleting Score Cap with ID: {}", id);
        ScoreCap existScoreCap = scoreCapRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("ScoreCap with id" + id + "not found"));
        scoreCapRepository.delete(existScoreCap);
        return true;
    }


}
