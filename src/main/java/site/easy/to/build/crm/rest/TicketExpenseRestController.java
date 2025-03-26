package site.easy.to.build.crm.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.LeadExpense;
import site.easy.to.build.crm.entity.TicketExpense;
import site.easy.to.build.crm.entity.TicketHisto;
import site.easy.to.build.crm.entity.TriggerLeadHisto;
import site.easy.to.build.crm.service.ticket.TicketExpenseService;
import site.easy.to.build.crm.service.ticket.TicketHistoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ticket-expenses")
public class TicketExpenseRestController {

    @Autowired
    private TicketExpenseService ticketExpenseService;

    @Autowired
    private TicketHistoService ticketHistoService;

    // Récupérer une dépense par son ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketExpense> getById(@PathVariable int id) {
        TicketExpense ticketExpense = ticketExpenseService.getLatestExpenseForTicketHisto(id);

        if (ticketExpense != null) {
            return ResponseEntity.ok(ticketExpense);
        } else {
            TicketExpense defaultTicketExpense = new TicketExpense();
            defaultTicketExpense.setId(0);
            defaultTicketExpense.setAmount(BigDecimal.ZERO);  // Utilisation de BigDecimal.ZERO pour un montant par défaut
            defaultTicketExpense.setCreatedAt(LocalDateTime.MIN);  // Valeur par défaut pour la date de création

            return ResponseEntity.ok(defaultTicketExpense);
        }
    }


    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalExpenses(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd[['T']HH:mm[:ss]]")
            LocalDateTime startDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd[['T']HH:mm[:ss]]")
            LocalDateTime endDate) {

        List<TicketHisto> triggerLeadHistos = ticketHistoService.getBetweenDate(startDate, endDate);
        BigDecimal total = BigDecimal.ZERO;

        for (TicketHisto triggerLead : triggerLeadHistos) {
            TicketExpense latestExpense = ticketExpenseService.getLatestExpenseForTicketHisto(triggerLead.getId());
            if (latestExpense != null && latestExpense.getAmount() != null) {
                total = total.add(latestExpense.getAmount());
            }
        }

        return ResponseEntity.ok(total);
    }


    // Mettre à jour une dépense
    @PutMapping("/modif/{id}")
    public ResponseEntity<TicketExpense> update(@PathVariable int id, @Valid @RequestBody TicketExpense updatedTicketExpense) {
        TicketExpense existingTicketExpense = new TicketExpense();

        existingTicketExpense.setAmount(updatedTicketExpense.getAmount());
        existingTicketExpense.setCreatedAt(updatedTicketExpense.getCreatedAt());
        existingTicketExpense.setTicketHisto(updatedTicketExpense.getTicketHisto());

        TicketExpense savedTicketExpense = ticketExpenseService.save(existingTicketExpense);
        return ResponseEntity.ok(savedTicketExpense); // 200 OK
    }

}