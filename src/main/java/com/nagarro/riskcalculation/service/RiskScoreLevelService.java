package com.nagarro.riskcalculation.service;//package com.nagarro.riskcalculation.service;
//
//import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
//import com.nagarro.riskcalculation.model.RiskScoreLevel;
//import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class RiskScoreLevelService {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private RiskScoreLevelRepository riskScoreLevelRepository;
//
//    public List<RiskScoreLevel> getAllRiskScoreLevels() {
//        log.info("Getting all Risk Score Levels");
//        return riskScoreLevelRepository.findAll();
//    }
//
//    public RiskScoreLevel getRiskScoreLevelById(int id) {
//        log.info("Getting Risk Score Level by ID: {}", id);
//        return riskScoreLevelRepository.findById(id).orElseThrow(() ->
//                new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//    }
//
//    public RiskScoreLevel createRiskScoreLevel(RiskScoreLevel riskScoreLevel) {
//        log.info("Creating Risk Score Level: {}", riskScoreLevel);
//        Optional<RiskScoreLevel> existingRiskScoreLevel = riskScoreLevelRepository.findByLevel(riskScoreLevel.getLevel());
//        if (existingRiskScoreLevel.isPresent()) {
//            log.info("RiskScoreLevel already exists for Level: {}", riskScoreLevel.getLevel());
//            throw new DataAlreadyExistsException("Data with the same Level already exists.");
//        }
//        return riskScoreLevelRepository.save(riskScoreLevel);
//    }
//
//
//
//    public RiskScoreLevel updateRiskScoreLevel(int id, RiskScoreLevel riskScoreLevel) {
//        log.info("Updating Risk Score Level with ID: {}", id);
//        RiskScoreLevel existingRiskScoreLevel = riskScoreLevelRepository.findById(id).orElseThrow(
//                () -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//        existingRiskScoreLevel.setLevel(riskScoreLevel.getLevel());
//        existingRiskScoreLevel.setMinScore(riskScoreLevel.getMinScore());
//        existingRiskScoreLevel.setMaxScore(riskScoreLevel.getMaxScore());
//        return riskScoreLevelRepository.save(existingRiskScoreLevel);
//    }
//
//    public boolean deleteRiskScoreLevel(int id) {
//        RiskScoreLevel existRiskScoreLevel = riskScoreLevelRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//        log.info("Deleting company risk score for company: {}", id);
//        riskScoreLevelRepository.delete(existRiskScoreLevel);
//        return true;
//    }
//    public List<String> getAllLevelNames() {
//        log.info("Returning the risk score level of all scores");
//        return riskScoreLevelRepository.findAllRiskLevelNames();
//    }
//}
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.advice.InvalidRangeException;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Slf4j
//    @Service
//    public class RiskScoreLevelService {
//
//        @Autowired
//        private RiskScoreLevelRepository riskScoreLevelRepository;
//
//        public List<RiskScoreLevel> getAllRiskScoreLevels() {
//            log.info("Getting all Risk Score Levels");
//            return riskScoreLevelRepository.findAll();
//        }
//
//        public RiskScoreLevel getRiskScoreLevelById(int id) {
//            log.info("Getting Risk Score Level by ID: {}", id);
//            return riskScoreLevelRepository.findById(id).orElseThrow(() ->
//                    new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//        }
//
//        public RiskScoreLevel createRiskScoreLevel(RiskScoreLevel riskScoreLevel) {
//            log.info("Creating Risk Score Level: {}", riskScoreLevel);
//        Optional<RiskScoreLevel> existingRiskScoreLevel = riskScoreLevelRepository.findByLevel(riskScoreLevel.getLevel());
//        if (existingRiskScoreLevel.isPresent()) {
//            log.info("RiskScoreLevel already exists for Level: {}", riskScoreLevel.getLevel());
//            throw new DataAlreadyExistsException("Data with the same Level already exists.");
//        }
//            // Check if any existing risk score level overlaps with the new range
//            checkRangeOverlap(riskScoreLevel);
//            // If no overlapping range exists, save the new risk score level
//            return riskScoreLevelRepository.save(riskScoreLevel);
//        }
//
//        public RiskScoreLevel updateRiskScoreLevel(int id, RiskScoreLevel riskScoreLevel) {
//            log.info("Updating Risk Score Level with ID: {}", id);
//
//            // Check if any other existing risk score level overlaps with the updated range
//            checkRangeOverlap(riskScoreLevel, id);
//
//            RiskScoreLevel existingRiskScoreLevel = riskScoreLevelRepository.findById(id).orElseThrow(
//                    () -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//            existingRiskScoreLevel.setLevel(riskScoreLevel.getLevel());
//            existingRiskScoreLevel.setMinScore(riskScoreLevel.getMinScore());
//            existingRiskScoreLevel.setMaxScore(riskScoreLevel.getMaxScore());
//            return riskScoreLevelRepository.save(existingRiskScoreLevel);
//        }
//
//        public boolean deleteRiskScoreLevel(int id) {
//            RiskScoreLevel existRiskScoreLevel = riskScoreLevelRepository.findById(id)
//                    .orElseThrow(() -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
//            log.info("Deleting company risk score for company: {}", id);
//            riskScoreLevelRepository.delete(existRiskScoreLevel);
//            return true;
//        }
//
//        public List<String> getAllLevelNames() {
//            log.info("Returning the risk score level of all scores");
//            return riskScoreLevelRepository.findAllRiskLevelNames();
//        }
//
//        private void checkRangeOverlap(RiskScoreLevel newRiskScoreLevel) {
//            List<RiskScoreLevel> existingLevels = riskScoreLevelRepository.findAll();
//            for (RiskScoreLevel existingLevel : existingLevels) {
//                if (rangesOverlap((int) existingLevel.getMinScore(), (int) existingLevel.getMaxScore(),
//                        (int) newRiskScoreLevel.getMinScore(), (int) newRiskScoreLevel.getMaxScore())) {
//                    throw new InvalidRangeException("The new range overlaps with an existing risk score level.");
//                }
//            }
//        }
//
//        private void checkRangeOverlap(RiskScoreLevel newRiskScoreLevel, int idToUpdate) {
//            List<RiskScoreLevel> existingLevels = riskScoreLevelRepository.findAll();
//            for (RiskScoreLevel existingLevel : existingLevels) {
//                // Skip checking against the risk score level being updated
//                if (existingLevel.getId() == idToUpdate) {
//                    continue;
//                }
//                if (rangesOverlap((int) existingLevel.getMinScore(), (int) existingLevel.getMaxScore(),
//                        (int) newRiskScoreLevel.getMinScore(), (int) newRiskScoreLevel.getMaxScore())) {
//                    throw new InvalidRangeException("The new range overlaps with an existing risk score level.");
//                }
//            }
//        }
//
//        private boolean rangesOverlap(int min1, int max1, int min2, int max2) {
//            return (min1 <= max2 && max1 >= min2);
//        }
//
//    }
@Slf4j
@Service
public class RiskScoreLevelService {

    @Autowired
    private RiskScoreLevelRepository riskScoreLevelRepository;
    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 100;
    public List<RiskScoreLevel> getAllRiskScoreLevels() {
        log.info("Getting all Risk Score Levels");
        return riskScoreLevelRepository.findAll();
    }

    public RiskScoreLevel getRiskScoreLevelById(int id) {
        log.info("Getting Risk Score Level by ID: {}", id);
        return riskScoreLevelRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
    }

    public RiskScoreLevel createRiskScoreLevel(RiskScoreLevel riskScoreLevel) {
        log.info("Creating Risk Score Level: {}", riskScoreLevel);

        validateRiskScoreLevel(riskScoreLevel);
        Optional<RiskScoreLevel> existingRiskScoreLevel = riskScoreLevelRepository.findByLevel(riskScoreLevel.getLevel());
        if (existingRiskScoreLevel.isPresent()) {
            log.info("RiskScoreLevel already exists for Level: {}", riskScoreLevel.getLevel());
            throw new DataAlreadyExistsException("Data with the same Level already exists.");
        }

        // Check if any existing risk score level overlaps with the new range
        checkRangeOverlap(riskScoreLevel);

        // If no overlapping range exists, save the new risk score level
        return riskScoreLevelRepository.save(riskScoreLevel);
    }

    public RiskScoreLevel updateRiskScoreLevel(int id, RiskScoreLevel riskScoreLevel) {
        log.info("Updating Risk Score Level with ID: {}", id);

        validateRiskScoreLevel(riskScoreLevel);

        // Check if any other existing risk score level overlaps with the updated range
        checkRangeOverlap(riskScoreLevel, id);

        RiskScoreLevel existingRiskScoreLevel = riskScoreLevelRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
        existingRiskScoreLevel.setLevel(riskScoreLevel.getLevel());
        existingRiskScoreLevel.setMinScore(riskScoreLevel.getMinScore());
        existingRiskScoreLevel.setMaxScore(riskScoreLevel.getMaxScore());
        return riskScoreLevelRepository.save(existingRiskScoreLevel);
    }

    public boolean deleteRiskScoreLevel(int id) {
        RiskScoreLevel existRiskScoreLevel = riskScoreLevelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("RiskScoreLevel with id " + id + " not found"));
        log.info("Deleting company risk score for company: {}", id);
        riskScoreLevelRepository.delete(existRiskScoreLevel);
        return true;
    }

    public List<String> getAllLevelNames() {
        log.info("Returning the risk score level of all scores");
        return riskScoreLevelRepository.findAllRiskLevelNames();
    }

    private void validateRiskScoreLevel(RiskScoreLevel riskScoreLevel) {
        if (riskScoreLevel.getMinScore() < MIN_SCORE || riskScoreLevel.getMinScore() > MAX_SCORE ||
                riskScoreLevel.getMaxScore() < MIN_SCORE || riskScoreLevel.getMaxScore() > MAX_SCORE) {
            throw new InvalidRangeException("Invalid Score Range,Scores should be between 1 to 100!");
        }
    }

    private void checkRangeOverlap(RiskScoreLevel newRiskScoreLevel) {
        List<RiskScoreLevel> existingLevels = riskScoreLevelRepository.findAll();
        for (RiskScoreLevel existingLevel : existingLevels) {
            if (rangesOverlap((int) existingLevel.getMinScore(), (int) existingLevel.getMaxScore(),
                    (int) newRiskScoreLevel.getMinScore(), (int) newRiskScoreLevel.getMaxScore())) {
                throw new InvalidRangeException("The new Range overlaps with Existing Range!");
            }
        }
    }

    private void checkRangeOverlap(RiskScoreLevel newRiskScoreLevel, int idToUpdate) {
        List<RiskScoreLevel> existingLevels = riskScoreLevelRepository.findAll();
        for (RiskScoreLevel existingLevel : existingLevels) {
            // Skip checking against the risk score level being updated
            if (existingLevel.getId() == idToUpdate) {
                continue;
            }
            if (rangesOverlap((int) existingLevel.getMinScore(), (int) existingLevel.getMaxScore(),
                    (int) newRiskScoreLevel.getMinScore(), (int) newRiskScoreLevel.getMaxScore())) {
                throw new InvalidRangeException("The new Range overlaps with Existing Range!");
            }
        }
    }

    private boolean rangesOverlap(int min1, int max1, int min2, int max2) {
        return (min1 <= max2 && max1 >= min2);
    }
}
