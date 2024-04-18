package com.nagarro.riskcalculation.service;
import com.nagarro.riskcalculation.model.JobExecution;
import com.nagarro.riskcalculation.model.JobExecutionDTO;
import com.nagarro.riskcalculation.model.JobStatus;
import com.nagarro.riskcalculation.repository.JobExecutionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class JobExecutionService {
    @Autowired
    private final JobExecutionRepository jobExecutionRepository;

   public void saveJobExecution(String failedCompanies,JobStatus status,String jobName)
    {
        JobExecution jobExecution = new JobExecution();
        jobExecution.setTimestamp(LocalDateTime.now());
        jobExecution.setStatus(status);
        jobExecution.setFailedCompanies(failedCompanies);
        jobExecution.setJobStatusName(jobName);
        jobExecutionRepository.save(jobExecution);
    }
    public List<JobExecutionDTO> getAllJobExecutions() {
        List<JobExecution> jobExecutions = jobExecutionRepository.findAll();
        List<JobExecutionDTO> jobExecutionDTOs = new ArrayList<>();
        for (JobExecution jobExecution : jobExecutions) {
            JobExecutionDTO jobExecutionDTO = new JobExecutionDTO();
            jobExecutionDTO.setId(jobExecution.getId());
            jobExecutionDTO.setTimestamp(jobExecution.getTimestamp());
            jobExecutionDTO.setFailedCompanies(jobExecution.getFailedCompanies());
            jobExecutionDTO.setStatus(jobExecution.getStatus());
            jobExecutionDTO.setJobStatusName(jobExecution.getJobStatusName());
            jobExecutionDTOs.add(jobExecutionDTO);
        }
        return jobExecutionDTOs;
    }
}
