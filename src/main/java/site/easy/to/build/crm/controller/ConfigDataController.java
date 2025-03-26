package site.easy.to.build.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.easy.to.build.crm.entity.ImportBudgetCustomer;
import site.easy.to.build.crm.entity.ImportCustomer;
import site.easy.to.build.crm.entity.ImportLeadTicket;
import site.easy.to.build.crm.service.customer.CustomerImportService;
import site.easy.to.build.crm.service.data.ConfigDataService;
import site.easy.to.build.crm.service.data.ImportBudgetCustomerService;
import site.easy.to.build.crm.service.data.ImportCustomerService;
import site.easy.to.build.crm.service.data.ImportLeadTicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/data/config")
public class ConfigDataController {
    private final ConfigDataService configDataService;
    private final ImportLeadTicketService importLeadTicketService;
    private final ImportCustomerService importCustomerService;
    private final ImportBudgetCustomerService importBudgetCustomerService;


    @Autowired
    public ConfigDataController(ConfigDataService configDataService,
                                ImportLeadTicketService importLeadTicketService, ImportCustomerService importCustomerService,
                                ImportBudgetCustomerService importBudgetCustomerService) {
        this.configDataService = configDataService;
        this.importLeadTicketService = importLeadTicketService;
        this.importCustomerService = importCustomerService;
        this.importBudgetCustomerService = importBudgetCustomerService;
    }

    @GetMapping("/resetData")
    public String showResetDataPage() {
        return "configData/reset";
    }

    @PostMapping("/resetData")
    public String resetData(Model model) {
        try {
            String message = configDataService.resetData(); // Logique de réinitialisation
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while resetting the data.");
        }
        return "configData/reset";
    }

    @GetMapping("/import-csv")
    public String importPage() {
        return "configData/import-csv";
    }


    @PostMapping("/import-csv")
    public String importLeadTicketCsvFile(
            @RequestParam("LeadTicketFile") MultipartFile leadTicketFile,
            @RequestParam("CustomerFile") MultipartFile customerFile,
            @RequestParam("BudgetCustomerFile") MultipartFile budgetCustomerFile,
            RedirectAttributes redirectAttributes, Model model) throws Exception {

        List<String> errorMessages = new ArrayList<>();
        List<ImportBudgetCustomer> importBudgetCustomers = new ArrayList<>();
        List<ImportCustomer> importCustomers = new ArrayList<>();
        List<ImportLeadTicket> importLeadTickets = new ArrayList<>();
        try {
            importCustomers = importCustomerService.checkCsv(customerFile);
        } catch (Exception e) {
            errorMessages
                    .add("Erreur dans le fichier '" + customerFile.getOriginalFilename() + "' : " + e.getMessage());
            errorMessages.add("\n");
        }

        try {
            importLeadTickets = importLeadTicketService.checkCsv(leadTicketFile, importCustomers);
        } catch (Exception e) {
            errorMessages
                    .add("Erreur dans le fichier '" + leadTicketFile.getOriginalFilename() + "' : " + e.getMessage());
            errorMessages.add("\n");
        }

        try {
            importCustomers = importCustomerService.checkCsv(customerFile);
            importBudgetCustomers = importBudgetCustomerService.checkCsv(budgetCustomerFile, importCustomers);
        } catch (Exception e) {
            errorMessages.add(
                    "Erreur dans le fichier '" + budgetCustomerFile.getOriginalFilename() + "' : " + e.getMessage());
            errorMessages.add("\n");
        }

        if (!errorMessages.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", String.join(" | ", errorMessages));
            System.out.println("Erreurs rencontrées : " + errorMessages);
            return "redirect:/data/config/import-csv"; // Retourne avec les erreurs
        }

        importCustomerService.importCSV(importCustomers);
        importBudgetCustomerService.importCsv(importBudgetCustomers);
        importLeadTicketService.importCsv(importLeadTickets);

        redirectAttributes.addFlashAttribute("message", "Import réussi !");
        System.out.println("Succès de l'import");
        return "redirect:/data/config/import-csv"; // Redirection en cas de succès

    }
}
