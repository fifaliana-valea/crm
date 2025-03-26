package site.easy.to.build.crm.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import site.easy.to.build.crm.Dto.LeadExpenseUpdateDto;
import site.easy.to.build.crm.entity.LeadExpense;
import site.easy.to.build.crm.entity.TriggerLeadHisto;
import site.easy.to.build.crm.service.lead.LeadExpenseService;
import site.easy.to.build.crm.service.lead.TriggerLeadHistoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lead-expenses")
public class LeadExpenseRestController {

    private final LeadExpenseService leadExpenseService;
    private final TriggerLeadHistoService triggerLeadHistoService;

    @Autowired
    public LeadExpenseRestController(LeadExpenseService leadExpenseService, TriggerLeadHistoService triggerLeadHisto) {
        this.leadExpenseService = leadExpenseService;
        this.triggerLeadHistoService = triggerLeadHisto;
    }


    @GetMapping("/{id}")
    public ResponseEntity<LeadExpense> getExpenseById(@PathVariable("id") int id) {
        Optional<LeadExpense> expense = Optional.ofNullable(leadExpenseService.findLatestByTriggerLeadHistoId(id));
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/leadExpense/{id}")
    public ResponseEntity<LeadExpense> getExpenseByIdLead(@PathVariable("id") int id) {
        Optional<LeadExpense> expense = Optional.ofNullable(leadExpenseService.findById(id));
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalExpenses(){

        List<TriggerLeadHisto> triggerLeadHistos = triggerLeadHistoService.getAll();
        BigDecimal total = BigDecimal.ZERO;

        for (TriggerLeadHisto triggerLead : triggerLeadHistos) {
            LeadExpense latestExpense = leadExpenseService.findLatestByTriggerLeadHistoId(triggerLead.getId());
            if (latestExpense != null && latestExpense.getAmount() != null) {
                total = total.add(latestExpense.getAmount());
            }
        }

        return ResponseEntity.ok(total);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalDateExpenses(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd[['T']HH:mm[:ss]]")
            LocalDateTime startDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd[['T']HH:mm[:ss]]")
            LocalDateTime endDate) {

        List<TriggerLeadHisto> triggerLeadHistos = triggerLeadHistoService.getTriggerLeadHistoBetweenDates(startDate,endDate);
        BigDecimal total = BigDecimal.ZERO;

        for (TriggerLeadHisto triggerLead : triggerLeadHistos) {
            LeadExpense latestExpense = leadExpenseService.findLatestByTriggerLeadHistoId(triggerLead.getId());
            if (latestExpense != null && latestExpense.getAmount() != null) {
                total = total.add(latestExpense.getAmount());
            }
        }

        return ResponseEntity.ok(total);
    }

    @PutMapping("/update")
    public ResponseEntity<LeadExpense> updateLeadExpense(@RequestBody LeadExpenseUpdateDto updateDto) {
        try {
            // 1. Valider les données reçues
            if (updateDto == null) {
                return ResponseEntity.badRequest().build();
            }
            TriggerLeadHisto leadhisto=new TriggerLeadHisto();
            leadhisto.setId(updateDto.getLeadId());
            // 3. Mettre à jour les champs
            LeadExpense expenseToUpdate = new LeadExpense();
            expenseToUpdate.setAmount(updateDto.getAmount());
            expenseToUpdate.setCreatedAt(updateDto.getCreatedAt());
            expenseToUpdate.setTriggerLeadHisto(leadhisto);
            LeadExpense updatedExpense = leadExpenseService.save(expenseToUpdate);

            // 5. Retourner la réponse
            return ResponseEntity.ok(updatedExpense);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}