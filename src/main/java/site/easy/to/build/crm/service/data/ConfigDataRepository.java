package site.easy.to.build.crm.service.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigDataRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional // Enclose method in a transaction
    public String resetDataBase() {
        try {
            // Disable foreign key checks
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // Truncate tables in safe order (children first)
            String[] tables = {
                    "employee",
                    "email_template",
                    "customer_login_info",
                    "trigger_lead",
                    "trigger_ticket",
                    "trigger_contract",
                    "lead_action",
                    "google_drive_file",
                    "file",
                    "contract_settings",
                    "lead_settings",
                    "ticket_settings",
                    "customer"
            };

            for (String table : tables) {
                entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            }

            // Re-enable foreign key checks
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            return "Data reset successfully.";
        } catch (Exception e) {
            // Transaction will automatically roll back on exception (if using @Transactional)
            e.printStackTrace();
            return "Failed to reset: " + e.getMessage();
        }
    }


}
