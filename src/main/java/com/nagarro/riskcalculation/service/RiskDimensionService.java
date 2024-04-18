package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.advice.RiskDimensionException;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import java.util.*;


@Slf4j
@Service
public class RiskDimensionService {
    @Autowired
    private RiskDimensionRepository riskDimensionRepository;

    public RiskDimension getRiskDimension(String dimension) {

        log.info("Getting RiskDimension by dimension: {}", dimension);
        return riskDimensionRepository.findById(dimension).orElseThrow(() ->
                new NoSuchElementException(("RiskDimension with id " + dimension + " not found")));
    }

    public List<RiskDimension> getAllRiskDimensions() {
        log.info("Getting all Dimensions");
        return riskDimensionRepository.findAll();
    }

    public boolean deleteRiskDimension(String dimension) {
        log.info("Deleting RiskDimension with dimension: {}", dimension);
        RiskDimension existRiskDimension = riskDimensionRepository.findById(dimension)
                .orElseThrow(() -> new RiskDimensionException("Company risk score not found"));
        log.info("Deleting company risk score for company: {}", dimension);
        riskDimensionRepository.deleteById(dimension);
        return true;
    }

    public List<RiskDimension> addOrUpdateDimensionWeight(List<RiskDimension> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list of dimensions weights cannot be empty");
        }
        Set<String> dimensionNames = new HashSet<>();

        for (RiskDimension dimension : list) {
            if (!dimensionNames.add(dimension.getDimension())) {
                throw new EntityExistsException("Duplicate dimension name: " + dimension.getDimension());
            }

            double dimensionValue = Double.valueOf(dimension.getWeight()) / 100.0;
            dimension.setWeight(Double.valueOf(String.valueOf(dimensionValue)));
        }
        List<RiskDimension> existingDimensions = riskDimensionRepository.findAll();

        existingDimensions.removeIf(existingDimension ->
                list.stream().anyMatch(updatedDimension ->
                        updatedDimension.getDimension().equals(existingDimension.getDimension())));

        double sum = list.stream()
                .mapToDouble(dimension -> Double.parseDouble(String.valueOf(dimension.getWeight())))
                .sum();
        if (sum != 1.0) {
            throw new IllegalArgumentException("Sum of weight values is not equal to 100");
        }
        riskDimensionRepository.deleteAll(existingDimensions);
        return riskDimensionRepository.saveAll(list);

    }
    public List<String> getAllDimensions() {
        List<RiskDimension> allRiskDimensions = riskDimensionRepository.findAll();
        List<String> allDimensions= new ArrayList<>();

        for (RiskDimension riskDimension : allRiskDimensions) {
            allDimensions.add(riskDimension.getDimension());
        }

        return allDimensions;
    }
}




