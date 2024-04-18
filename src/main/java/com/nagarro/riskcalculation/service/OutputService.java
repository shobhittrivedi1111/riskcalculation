package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.model.Output;
import com.nagarro.riskcalculation.repository.OutputRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class OutputService {

    @Autowired
    private OutputRepository outputRepository;

    public Output saveOutput(Output output) {
        log.info("Saving Output");
        Optional<Output> existingOutput = outputRepository.findById(output.getCompanyName());
        if (existingOutput.isPresent()) {
            log.info("Output already exists for ID: {}", output.getCompanyName());
            return existingOutput.get(); // Return the existing output
        }
        return outputRepository.save(output);
    }


//    public Output getOutputByCompanyName(String companyName) {
//        log.info("Retrieving Output of company {}",companyName);
//        return outputRepository.findById(companyName).orElseThrow(()->
//                new NoSuchElementException("Output not found for company: " + companyName));
//    }
    public List<Output> getAllOutput(){
        log.info("Retrieving Output for all company");
        return outputRepository.findAll();
    }

    public boolean deleteOutputByCompany(String companyName) {
        Output existingoutput= outputRepository.findById(companyName)
                        .orElseThrow(()-> new NoSuchElementException("Output with company" + companyName + " not found"));
        log.info("Deleted Output with ID: {}", companyName);
        outputRepository.delete(existingoutput);
        return true;
    }
}

