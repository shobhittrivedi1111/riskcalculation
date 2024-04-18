package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution,Long> {
}
