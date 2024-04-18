package com.nagarro.riskcalculation.repository;

import com.nagarro.riskcalculation.model.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRepository extends JpaRepository<Output, String> {

    Output findByCompanyName(String companyName);
}
