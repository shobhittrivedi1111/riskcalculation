package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.model.RiskScoreLevel;
import com.nagarro.riskcalculation.model.ScoreCap;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import com.nagarro.riskcalculation.repository.RiskScoreLevelRepository;
import com.nagarro.riskcalculation.repository.ScoreCapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class TotalRiskCappedScoreService {
    @Autowired
    private CompanyRiskScoreRepository companyRiskScoreRepository;
    @Autowired
    private RiskScoreLevelRepository riskScoreLevelRepository;
    @Autowired
    private ScoreCapRepository scoreCapRepository;

    public Double totalRiskCappedScore(String companyName) {
        // Fetch data from repositories
        List<CompanyRiskScore> companyRiskScoreList = companyRiskScoreRepository.findAll();
        List<RiskScoreLevel> riskScoreLevelList = riskScoreLevelRepository.findAll();
        List<ScoreCap> scoreCapList = scoreCapRepository.findAll();
        // Find the CompanyRiskScore by companyName
        CompanyRiskScore companyRiskScore = companyRiskScoreRepository.findById(companyName).get();
        Map<String, String> dimensions = companyRiskScore.getDimensions();
        Map<String, Integer> levelCountMap = new HashMap<>();
        // Calculate the count of RiskScoreLevels for each dimension value
        for (Map.Entry<String, String> entry : dimensions.entrySet()) {
            String dimension = entry.getKey();
            Double value = Double.valueOf(entry.getValue());
            log.info("Dimension: {}, Value: {}", dimension, value);
            String level = findLevelForValue(value, riskScoreLevelList);
            // Increment the count for the level in the levelCountMap
            levelCountMap.put(level, levelCountMap.getOrDefault(level, 0) + 1);
        }

       //  Calculate the total risk capped score
               Double totalRiskCappedScore = evaluateTotalRiskCappedScore(levelCountMap, scoreCapList);
              log.info("Total Risk Capped Score of company : {} is {}",
                    companyRiskScore.getCompanyName(), totalRiskCappedScore);

        // Print the level count map
        for (Map.Entry<String, Integer> entry1 : levelCountMap.entrySet()) {
            String level = entry1.getKey();
            int count = entry1.getValue();
            log.info("Level: {}, Count: {}", level, count);
        }
        levelCountMap.clear();
             return totalRiskCappedScore;
    }
// Find the RiskScoreLevel for a given value
    private String findLevelForValue(Double value, List<RiskScoreLevel> riskScoreLevels) {
        for (RiskScoreLevel level : riskScoreLevels) {
            Double min = level.getMinScore();
            Double max = level.getMaxScore();
            if (value >= min && value <= max) {
                return level.getLevel();
            }
        }
        return "Level in Range not found";
    }

//        private Double evaluateTotalRiskCappedScore(Map<String, Integer> levelCountMap, List<ScoreCap> scoreCaps) {
//        Double totalRiskCappedScore = 100.0; // Default value if no score cap is found
//        boolean isMatchFound = false;
//        for (ScoreCap scoreCap : scoreCaps) {
//            String condition = scoreCap.getCondition();
//            Double cappedScore = Double.parseDouble(scoreCap.getTotal_risk_capped_score());
//            // Check if the condition matches any key-value pair in the levelCountMap
//            for (Map.Entry<String, Integer> entry : levelCountMap.entrySet()) {
//                String level = entry.getKey();
//                Integer count = Integer.parseInt(String.valueOf(entry.getValue()));
//                String levelCountString = count + " " + level; // Concatenate count and level
//                if (condition.equalsIgnoreCase(levelCountString)) {
//                    if (!isMatchFound || cappedScore < totalRiskCappedScore) {
//                        totalRiskCappedScore = cappedScore;
//                        isMatchFound = true;
//                    }
//                }
//            }
//        }
//        return totalRiskCappedScore;
//    }
    // Evaluate the total risk capped score based on level counts and score caps
private Double evaluateTotalRiskCappedScore(Map<String, Integer> levelCountMap, List<ScoreCap> scoreCaps) {
        Double totalRiskCappedScore = 100.0; // Default value if no score cap is found
        List<Double> totalRiskCappedScoreList = new ArrayList<>();
        for (ScoreCap scoreCap : scoreCaps) {
            Integer conditionValue = scoreCap.getConditionValue();
            String conditionName = scoreCap.getRiskScoreLevel().getLevel();
            for (Map.Entry<String, Integer> entry : levelCountMap.entrySet()) {
                if (conditionName.equals(entry.getKey()) && conditionValue == entry.getValue())
                    totalRiskCappedScoreList.add(Double.valueOf(scoreCap.getTotal_risk_capped_score()));
            }
        }
        if (totalRiskCappedScoreList.isEmpty())
            return totalRiskCappedScore;
        else
            return totalRiskCappedScoreList.stream().min(Comparator.naturalOrder()).get();
    }
}




