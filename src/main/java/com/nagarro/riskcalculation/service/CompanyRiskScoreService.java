package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.CompanyRiskScore;
import com.nagarro.riskcalculation.repository.CompanyRiskScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import java.util.List;

@Slf4j
@Validated
@Service
public class CompanyRiskScoreService {

    @Autowired
    private CompanyRiskScoreRepository companyRiskScoreRepository;

    public CompanyRiskScore createCompanyRiskScore(CompanyRiskScore companyRiskScore) {
        log.info("Creating company risk score: {}", companyRiskScore);

        String companyName = companyRiskScore.getCompanyName().toLowerCase();

        Optional<CompanyRiskScore> existingCompanyRiskScore = companyRiskScoreRepository.findById(companyName);
        if (existingCompanyRiskScore.isPresent()) {
            log.info("Company risk score already exists for ID: {}", companyName);
            throw new DataAlreadyExistsException("Company Name Already Exists!");
        }

        // Check for duplicate keys in dimensions map
//        if (hasDuplicateKeys(companyRiskScore.getDimensions())) {
//            throw new IllegalArgumentException("Duplicate keys are not allowed in the dimensions map.");
//        }

        return companyRiskScoreRepository.save(companyRiskScore);
    }




    public List<CompanyRiskScore> getAllCompanyRiskScore() {
        log.info("Getting all company risk scores");
        return companyRiskScoreRepository.findAll();
    }

//    public CompanyRiskScore getCompanyRiskScore(String companyName) {
//        log.info("Getting company risk score for company By Id: {}", companyName);
//        return companyRiskScoreRepository.findAll().stream()
//                .filter(companyRiskScore -> companyRiskScore.getCompanyName().equalsIgnoreCase(companyName))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Company risk score with " + companyName + " not found"));
//    }
        public CompanyRiskScore getCompanyRiskScore(String companyName) {
        log.info("Getting company risk score for company By Id: {}", companyName);
        return companyRiskScoreRepository.findById(companyName)
                .orElseThrow(() -> new NoSuchElementException("Company risk score with " + companyName + " not found"));
    }






public CompanyRiskScore updateCompanyRiskScore(String companyName,CompanyRiskScore updatedCompanyRiskScore) {
    String lowercaseCompanyName = companyName.toLowerCase();

//    CompanyRiskScore existingCompanyRiskScore = companyRiskScoreRepository.findAll().stream()
//            .filter(companyRiskScore -> companyRiskScore.getCompanyName().equalsIgnoreCase(lowercaseCompanyName))
//            .findFirst()
//            .orElseThrow(() -> new NoSuchElementException("Company risk score with " + lowercaseCompanyName + " not found"));


    // Check for duplicate keys in updated dimensions map
//    if (hasDuplicateKeys(updatedCompanyRiskScore.getDimensions())) {
//        throw new IllegalArgumentException("Duplicate keys are not allowed in the dimensions map.");
//    }
    CompanyRiskScore existingCompanyRiskScore = companyRiskScoreRepository.findById(companyName)
            .orElseThrow(() -> new NoSuchElementException("Company risk score with " + lowercaseCompanyName + " not found"));


    existingCompanyRiskScore.setDimensions(updatedCompanyRiskScore.getDimensions());
    log.info("Updating company risk score for company: {}", lowercaseCompanyName);
    return companyRiskScoreRepository.save(existingCompanyRiskScore);
}

    public boolean deleteCompanyRiskScore(String companyName) {
        String lowercaseCompanyName = companyName.toLowerCase();

//        CompanyRiskScore existingCompanyRiskScore = companyRiskScoreRepository.findAll().stream()
//                .filter(companyRiskScore -> companyRiskScore.getCompanyName().equalsIgnoreCase(lowercaseCompanyName))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Company risk score with " + lowercaseCompanyName + " not found"));
        CompanyRiskScore existingCompanyRiskScore = companyRiskScoreRepository.findById(companyName)
                .orElseThrow(() -> new NoSuchElementException("Company risk score with " + lowercaseCompanyName + " not found"));

        log.info("Deleting company risk score for company: {}", lowercaseCompanyName);
        companyRiskScoreRepository.delete(existingCompanyRiskScore);
        return true;
    }

    public Set<String> getAllDimensionKeys() {
        List<CompanyRiskScore> allCompanyRiskScores = companyRiskScoreRepository.findAll();
        Set<String> allDimensionKeys = new HashSet<>();

        for (CompanyRiskScore companyRiskScore : allCompanyRiskScores) {
            allDimensionKeys.addAll(companyRiskScore.getDimensions().keySet());
        }

        return allDimensionKeys;
    }
    private boolean hasDuplicateKeys(Map<String, String> dimensions) {
        Set<String> keys = new HashSet<>();
        for (String key : dimensions.keySet()) {
            if (keys.contains(key)) {
                return true; // Duplicate key found
            }
            keys.add(key);
        }
        return false; // No duplicate keys found
    }


}
/*
    public CompanyRiskScore createCompanyRiskScore(CompanyRiskScore companyRiskScore) {
        log.info("Creating company risk score: {}", companyRiskScore);

        String companyName = companyRiskScore.getCompanyName().toLowerCase();

        Optional<CompanyRiskScore> existingCompanyRiskScore = companyRiskScoreRepository.findById(companyName);
        if (existingCompanyRiskScore.isPresent()) {
            log.info("Company risk score already exists for ID: {}", companyName);
            throw new DataAlreadyExistsException("Company Name Already Exists!");
        }

        // Check for duplicate keys in dimensions map
        if (hasDuplicateKeys(companyRiskScore.getDimensions())) {
            throw new IllegalArgumentException("Duplicate keys are not allowed in the dimensions map.");
        }

        // Add new keys to other companies with value initialized to 0
        List<CompanyRiskScore> allCompanyRiskScores = companyRiskScoreRepository.findAll();
        Set<String> newKeys = new HashSet<>();

        for (String key : companyRiskScore.getDimensions().keySet()) {
            boolean isNewKey = true;
            for (CompanyRiskScore existingCompany : allCompanyRiskScores) {
                if (existingCompany.getDimensions().containsKey(key)) {
                    isNewKey = false;
                    break;
                }
            }
            if (isNewKey) {
                newKeys.add(key);
            }
        }

        for (CompanyRiskScore existingCompany : allCompanyRiskScores) {
            for (String newKey : newKeys) {
                existingCompany.getDimensions().put(newKey, String.valueOf(0));
            }
            companyRiskScoreRepository.save(existingCompany);
        }

        return companyRiskScoreRepository.save(companyRiskScore);
    }
*/
//    public CompanyRiskScore updateCompanyRiskScore(String companyName, CompanyRiskScore updatedCompanyRiskScore) {
//        String lowercaseCompanyName = companyName.toLowerCase();
//
//        CompanyRiskScore existingCompanyRiskScore = companyRiskScoreRepository.findAll().stream()
//                .filter(companyRiskScore -> companyRiskScore.getCompanyName().equalsIgnoreCase(lowercaseCompanyName))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Company risk score with " + lowercaseCompanyName + " not found"));
//
//        // Check for duplicate keys in updated dimensions map
//        if (hasDuplicateKeys(updatedCompanyRiskScore.getDimensions())) {
//            throw new IllegalArgumentException("Duplicate keys are not allowed in the dimensions map.");
//        }
//
//        // Update existingCompanyRiskScore with the keys and values from updatedCompanyRiskScore
//      Map<String, String> existingDimensions = existingCompanyRiskScore.getDimensions();
//         Map<String, String> updatedDimensions = updatedCompanyRiskScore.getDimensions();
//        // Remove deleted keys from all companies' dimensions maps
//        removeDeletedKeys(existingDimensions, updatedDimensions, companyRiskScoreRepository.findAll());
//
//
//        for (Map.Entry<String, String> entry : updatedDimensions.entrySet()) {
//            String key = entry.getKey();
//            Integer value = Integer.valueOf(entry.getValue());
//
//            if (existingDimensions.containsKey(key)) {
//                // Update the existing key with the new value
//                existingDimensions.put(key, String.valueOf(value));
//            } else {
//                // Add a new key with the value in the existing company
//                existingDimensions.put(key, String.valueOf(value));
//
//                // Add the new key with value 0 to other companies
//                addNewKeyToOtherCompanies(key, companyRiskScoreRepository.findAll(), lowercaseCompanyName);
//            }
//        }
//
//        log.info("Updating company risk score for company: {}", lowercaseCompanyName);
//        return companyRiskScoreRepository.save(existingCompanyRiskScore);
//    }
//
//    private void addNewKeyToOtherCompanies(String newKey, List<CompanyRiskScore> allCompanies, String company) {
//        for (CompanyRiskScore companyRiskScore : allCompanies) {
//            if (!companyRiskScore.getCompanyName().equalsIgnoreCase(company)) {
//                companyRiskScore.getDimensions().put(newKey, String.valueOf(0));
//                companyRiskScoreRepository.save(companyRiskScore);
//            }
//        }
//    }
//    private void removeDeletedKeys(@NotNull(message = "Dimensions map cannot be null") Map<String, String> existingDimensions, Map<String, String> updatedDimensions,
//                                   List<CompanyRiskScore> allCompanies) {
//        Set<String> deletedKeys = new HashSet<>();
//        for (String key : existingDimensions.keySet()) {
//            if (!updatedDimensions.containsKey(key)) {
//                deletedKeys.add(key);
//            }
//        }
//
//        for (CompanyRiskScore companyRiskScore : allCompanies) {
//            Map<String, String> companyDimensions = companyRiskScore.getDimensions();
//            for (String deletedKey : deletedKeys) {
//                companyDimensions.remove(deletedKey);
//            }
//            companyRiskScoreRepository.save(companyRiskScore);
//        }
//    }