package com.nagarro.riskcalculation.testservice;

import com.nagarro.riskcalculation.model.JobExecution;
import com.nagarro.riskcalculation.model.JobExecutionDTO;
import com.nagarro.riskcalculation.model.JobStatus;
import com.nagarro.riskcalculation.repository.JobExecutionRepository;
import com.nagarro.riskcalculation.service.JobExecutionService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class JobExecutionServiceTest {

    @Mock
    private JobExecutionRepository mockJobExecutionRepository;

    @Captor
    private ArgumentCaptor<JobExecution> jobExecutionCaptor;

    @InjectMocks
    private JobExecutionService jobExecutionService;



    @Test
    public void testSaveJobExecution() {
        String failedCompanies = "CompanyA,CompanyB";
        JobStatus status = JobStatus.FAILURE;
        String jobName = "JobA";

        jobExecutionService.saveJobExecution(failedCompanies, status, jobName);

        Mockito.verify(mockJobExecutionRepository, Mockito.times(1)).save(jobExecutionCaptor.capture());
        JobExecution savedJobExecution = jobExecutionCaptor.getValue();

        assertEquals(failedCompanies, savedJobExecution.getFailedCompanies());
        assertEquals(status, savedJobExecution.getStatus());
        assertEquals(jobName, savedJobExecution.getJobStatusName());
    }

    @Test
    public void testGetAllJobExecutions() {
        List<JobExecution> mockJobExecutions = createMockJobExecutions();

        Mockito.when(mockJobExecutionRepository.findAll()).thenReturn(mockJobExecutions);

        List<JobExecutionDTO> result = jobExecutionService.getAllJobExecutions();

        assertEquals(mockJobExecutions.size(), result.size());
        for (int i = 0; i < mockJobExecutions.size(); i++) {
            JobExecution mockJobExecution = mockJobExecutions.get(i);
            JobExecutionDTO jobExecutionDTO = result.get(i);

            assertEquals(mockJobExecution.getId(), jobExecutionDTO.getId());
            assertEquals(mockJobExecution.getTimestamp(), jobExecutionDTO.getTimestamp());
            assertEquals(mockJobExecution.getFailedCompanies(), jobExecutionDTO.getFailedCompanies());
            assertEquals(mockJobExecution.getStatus(), jobExecutionDTO.getStatus());
            assertEquals(mockJobExecution.getJobStatusName(), jobExecutionDTO.getJobStatusName());
        }
    }

    private List<JobExecution> createMockJobExecutions() {
        List<JobExecution> jobExecutions = new ArrayList<>();

        JobExecution jobExecution1 = new JobExecution();
        jobExecution1.setId(1L);
        jobExecution1.setTimestamp(LocalDateTime.now());
        jobExecution1.setStatus(JobStatus.SUCCESS);
        jobExecution1.setFailedCompanies("");
        jobExecution1.setJobStatusName("JobA");
        jobExecutions.add(jobExecution1);

        JobExecution jobExecution2 = new JobExecution();
        jobExecution2.setId(2L);
        jobExecution2.setTimestamp(LocalDateTime.now());
        jobExecution2.setStatus(JobStatus.FAILURE);
        jobExecution2.setFailedCompanies("CompanyX,CompanyY");
        jobExecution2.setJobStatusName("JobB");
        jobExecutions.add(jobExecution2);

        return jobExecutions;
    }
}
