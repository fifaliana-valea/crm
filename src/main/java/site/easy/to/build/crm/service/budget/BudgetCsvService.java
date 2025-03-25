package site.easy.to.build.crm.service.budget;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.Dto.BudgetDto;
import site.easy.to.build.crm.entity.Budget;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.import_csv.CSVFile;
import site.easy.to.build.crm.import_csv.ConstraintCSV;
import site.easy.to.build.crm.repository.BudgetRepository;
import site.easy.to.build.crm.repository.CustomerRepository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetCsvService {
    private final CustomerRepository customerRepository;
    private final BudgetRepository budgetRepository;

    public BudgetCsvService(CustomerRepository customerRepository, BudgetRepository budgetRepository) {
        this.customerRepository = customerRepository;
        this.budgetRepository = budgetRepository;
    }

    public CSVFile<BudgetDto> processCSVFile(MultipartFile file) {
        CSVFile<BudgetDto> csvFile = new CSVFile<>(file, ";");

        try {
            // Ajouter les contraintes de validation
            csvFile.addConstraint("Budget", ConstraintCSV.BIG_DECIMAL_POSITIVE)
                    .addConstraint("customer_email", ConstraintCSV.EMAIL_VALIDATOR);

            // Lire et transformer les données
            csvFile.readAndTransform(v ->
                    new BudgetDto((String)v.get("customer_email"), (BigDecimal)v.get("Budget"))
            );

        } catch (Exception e) {
            csvFile.getErrors().add("Erreur lors du traitement du fichier: " + e.getMessage());
        }

        return csvFile;
    }

    public void saveAll(List<Budget> budgets) {
        if (budgets == null || budgets.isEmpty()) {
            throw new IllegalArgumentException("La liste des budgets ne peut pas être vide");
        }

        try {
            budgetRepository.saveAll(budgets);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des budgets: " + e.getMessage(), e);
        }
    }

    public List<Budget> convertDtoToEntity(CSVFile<BudgetDto> csvFile, List<String> errors) {
        List<Budget> budgets = new ArrayList<>();

        if (csvFile == null || csvFile.getData() == null) {
            errors.add("Aucune donnée CSV à traiter");
            return budgets;
        }

        int lineNumber = 1;
        for (BudgetDto dto : csvFile.getData()) {
            try {
                Budget budget = new Budget();

                // Validation du budget
                if (dto.getBudget() == null) {
                    errors.add(String.format("Ligne %d: %s - Détails: %s",
                            lineNumber,"Le budget ne peut pas être null", dto.toString()));
                }
                if (dto.getBudget().compareTo(BigDecimal.ZERO) < 0) {
                    errors.add(String.format("Ligne %d: %s - Détails: %s",
                            lineNumber,"Le budget ne peut pas être négatif", dto.toString()));
                }
                budget.setAmount(dto.getBudget());
                budget.setCreatedAt(LocalDate.now());

                // Recherche du client
                Customer customer = customerRepository.findByEmail(dto.getCustomerEmail());
                if (customer == null) {
                    errors.add(String.format("Ligne %d: %s - Détails: %s",
                            lineNumber,"Client non trouvé avec l'email: ", dto.getCustomerEmail()));
                }
                budget.setCustomer(customer);

                budgets.add(budget);
            } catch (Exception e) {
                errors.add(String.format("Ligne %d: %s - Détails: %s",
                        lineNumber, e.getMessage(), dto.toString()));
            }
            lineNumber++;
        }

        return budgets;
    }

    public CSVFile<BudgetDto> processBudgetCSV(String filePath) {
        CSVFile<BudgetDto> csvFile = new CSVFile<>(null, ";");

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            csvFile = new CSVFile<>(new MultipartFileAdapter(
                    "file",
                    Paths.get(filePath).getFileName().toString(),
                    "text/csv",
                    content.getBytes(StandardCharsets.UTF_8)
            ), ";");

            csvFile.addConstraint("Budget", ConstraintCSV.BIG_DECIMAL_POSITIVE)
                    .addConstraint("customer_email", ConstraintCSV.EMAIL_VALIDATOR);

            csvFile.readAndTransform(v ->
                    new BudgetDto((String)v.get("customer_email"), (BigDecimal)v.get("Budget"))
            );

        } catch (IOException e) {
            csvFile.getErrors().add("Erreur de lecture du fichier: " + e.getMessage());
        } catch (Exception e) {
            csvFile.getErrors().add("Erreur de traitement: " + e.getMessage());
        }

        return csvFile;
    }

    // Classe d'adaptateur simple pour MultipartFile
    private static class MultipartFileAdapter implements MultipartFile {
        private final String name;
        private final String originalFilename;
        private final String contentType;
        private final byte[] content;

        public MultipartFileAdapter(String name, String originalFilename, String contentType, byte[] content) {
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.content = content;
        }

        @Override public String getName() { return name; }
        @Override public String getOriginalFilename() { return originalFilename; }
        @Override public String getContentType() { return contentType; }
        @Override public boolean isEmpty() { return content.length == 0; }
        @Override public long getSize() { return content.length; }
        @Override public byte[] getBytes() { return content; }
        @Override public InputStream getInputStream() { return new ByteArrayInputStream(content); }
        @Override public void transferTo(File dest) throws IOException, IllegalStateException {
            Files.write(dest.toPath(), content);
        }
    }
}
