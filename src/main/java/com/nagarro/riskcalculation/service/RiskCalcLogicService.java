package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.advice.DataAlreadyExistsException;
import com.nagarro.riskcalculation.model.RiskCalcLogic;
import com.nagarro.riskcalculation.repository.RiskCalcLogicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class RiskCalcLogicService {
    @Autowired
    RiskCalcLogicRepository riskCalcLogicRepository;

    public List<RiskCalcLogic> getAllRiskCalcLogics() {
        log.info("Getting all formulas");
        return riskCalcLogicRepository.findAll();
    }

    public RiskCalcLogic getRiskCalcLogicById(int id) {
        log.info("Getting formula by id: {}", id);
        return riskCalcLogicRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Formula with id " + id + " not found"));
    }

    public RiskCalcLogic createRiskCalcLogic(RiskCalcLogic riskCalcLogic) {
        log.info("Creating formula: {}", riskCalcLogic);

        Optional<RiskCalcLogic> existingRiskCalcLogic = riskCalcLogicRepository.findByElementName(riskCalcLogic.getElementName());
        if (existingRiskCalcLogic.isPresent()) {
            log.info("Element exists for name: {}", riskCalcLogic.getElementName());
//            return existingRiskCalcLogic.get(); // Return the existing RiskCalcLogic
            throw new DataAlreadyExistsException("Element Name Already Present!");
        }
        return riskCalcLogicRepository.save(riskCalcLogic);
    }


    public RiskCalcLogic updateRiskCalcLogic(int id, RiskCalcLogic updatedRiskCalcLogic) {
        log.info("Updating formula with id: {}", id);
        RiskCalcLogic existingRiskCalcLogic = riskCalcLogicRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Formula with id " + id + " not found"));
        existingRiskCalcLogic.setElementName(updatedRiskCalcLogic.getElementName());
        existingRiskCalcLogic.setFormula(updatedRiskCalcLogic.getFormula());
        return riskCalcLogicRepository.save(existingRiskCalcLogic);
    }

    public boolean deleteRiskCalcLogic(int id) {
        RiskCalcLogic existRiskCalcLogic = riskCalcLogicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Formula with id " + id + " not found"));
        log.info("Deleting company risk score for company: {}", id);
        riskCalcLogicRepository.delete(existRiskCalcLogic);
        return true;
    }
    public List<String> getAllElementNames() {
        List<RiskCalcLogic> riskCalcLogics = riskCalcLogicRepository.findAll();
        List<String> elementNames = new ArrayList<>();
        for (RiskCalcLogic riskCalcLogic : riskCalcLogics) {
            elementNames.add(riskCalcLogic.getElementName());
        }
        return elementNames;
    }
}

