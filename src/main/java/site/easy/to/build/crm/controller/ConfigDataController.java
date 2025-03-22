package site.easy.to.build.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.service.data.ConfigDataService;

@Controller
@RequestMapping("/data/config")
public class ConfigDataController {
    private final ConfigDataService configDataService;

    @Autowired
    public ConfigDataController(ConfigDataService configDataService){
        this.configDataService = configDataService;
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
}
