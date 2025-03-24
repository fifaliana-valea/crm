package site.easy.to.build.crm.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.TicketExpense;
import site.easy.to.build.crm.service.ticket.TicketExpenseService;

@RestController
@RequestMapping("/api/ticket-expenses")
public class TicketExpenseRestController {

    @Autowired
    private TicketExpenseService ticketExpenseService;

    // Récupérer une dépense par son ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketExpense> getById(@PathVariable int id) {
        TicketExpense ticketExpense = ticketExpenseService.getLatestExpenseForTicketHisto(id);
        if (ticketExpense != null) {
            return ResponseEntity.ok(ticketExpense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mettre à jour une dépense
    @PutMapping("/modif/{id}")
    public ResponseEntity<TicketExpense> update(@PathVariable int id, @Valid @RequestBody TicketExpense updatedTicketExpense) {
        TicketExpense existingTicketExpense = ticketExpenseService.getLatestExpenseForTicketHisto(id);

        if (existingTicketExpense == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        existingTicketExpense.setAmount(updatedTicketExpense.getAmount());
        existingTicketExpense.setCreatedAt(updatedTicketExpense.getCreatedAt());
        existingTicketExpense.setTicketHisto(updatedTicketExpense.getTicketHisto());

        TicketExpense savedTicketExpense = ticketExpenseService.save(existingTicketExpense);
        return ResponseEntity.ok(savedTicketExpense); // 200 OK
    }

}