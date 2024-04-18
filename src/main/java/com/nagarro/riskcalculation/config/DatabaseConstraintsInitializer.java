package com.nagarro.riskcalculation.config;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
public class DatabaseConstraintsInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private boolean constraintsAdded = false;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!constraintsAdded) {
            // Check if the constraints already exist in the database
            if (!checkConstraintsExist()) {
                // Add the constraints using SQL queries
                String sql = "ALTER TABLE Risk_Score_Level " +
                        "ADD CONSTRAINT chk_min_score CHECK (min_score >= 1), " +
                        "ADD CONSTRAINT chk_max_score CHECK (max_score <= 100), " +
                        "ADD CONSTRAINT chk_min_less_than_max CHECK (min_score < max_score)";

                entityManager.createNativeQuery(sql).executeUpdate();
            }
            constraintsAdded = true;
        }
    }

    private boolean checkConstraintsExist() {
        String sql = "SELECT COUNT(*) FROM information_schema.check_constraints " +
                "WHERE constraint_schema = 'risk_calculation25' " +
                "AND constraint_name IN ('chk_min_score', 'chk_max_score', 'chk_min_less_than_max')";

        Number count = (Number) entityManager.createNativeQuery(sql).getSingleResult();

        return count != null && count.intValue() == 3;
    }
}


