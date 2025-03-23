package site.easy.to.build.crm.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.service.ticket.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    // Récupérer un ticket par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        Ticket ticket = ticketService.findByTicketId(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Créer un nouveau ticket
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.save(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    // Supprimer un ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        Ticket ticket = ticketService.findByTicketId(id);
        if (ticket != null) {
            ticketService.delete(ticket);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer les tickets d'un manager
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Ticket>> getTicketsByManagerId(@PathVariable int managerId) {
        List<Ticket> tickets = ticketService.findManagerTickets(managerId);
        return ResponseEntity.ok(tickets);
    }

    // Récupérer les tickets d'un employé
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Ticket>> getTicketsByEmployeeId(@PathVariable int employeeId) {
        List<Ticket> tickets = ticketService.findEmployeeTickets(employeeId);
        return ResponseEntity.ok(tickets);
    }

    // Récupérer tous les tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }

    // Récupérer les tickets d'un client
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Ticket>> getTicketsByCustomerId(@PathVariable int customerId) {
        List<Ticket> tickets = ticketService.findCustomerTickets(customerId);
        return ResponseEntity.ok(tickets);
    }

    // Récupérer les tickets récents pour un manager
    @GetMapping("/recent/manager/{managerId}")
    public ResponseEntity<List<Ticket>> getRecentTicketsForManager(
            @PathVariable int managerId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Ticket> recentTickets = ticketService.getRecentTickets(managerId, limit);
        return ResponseEntity.ok(recentTickets);
    }

    // Récupérer les tickets récents pour un employé
    @GetMapping("/recent/employee/{employeeId}")
    public ResponseEntity<List<Ticket>> getRecentTicketsForEmployee(
            @PathVariable int employeeId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Ticket> recentTickets = ticketService.getRecentEmployeeTickets(employeeId, limit);
        return ResponseEntity.ok(recentTickets);
    }

    // Récupérer les tickets récents pour un client
    @GetMapping("/recent/customer/{customerId}")
    public ResponseEntity<List<Ticket>> getRecentTicketsForCustomer(
            @PathVariable int customerId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Ticket> recentTickets = ticketService.getRecentCustomerTickets(customerId, limit);
        return ResponseEntity.ok(recentTickets);
    }

    // Compter le nombre de tickets assignés à un employé
    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countTicketsByEmployeeId(@PathVariable int employeeId) {
        long count = ticketService.countByEmployeeId(employeeId);
        return ResponseEntity.ok(count);
    }

    // Compter le nombre de tickets assignés à un manager
    @GetMapping("/count/manager/{managerId}")
    public ResponseEntity<Long> countTicketsByManagerId(@PathVariable int managerId) {
        long count = ticketService.countByManagerId(managerId);
        return ResponseEntity.ok(count);
    }

    // Compter le nombre de tickets pour un client
    @GetMapping("/count/customer/{customerId}")
    public ResponseEntity<Long> countTicketsByCustomerId(@PathVariable int customerId) {
        long count = ticketService.countByCustomerCustomerId(customerId);
        return ResponseEntity.ok(count);
    }

    // Supprimer tous les tickets d'un client
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteTicketsByCustomer(@PathVariable int customerId) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        ticketService.deleteAllByCustomer(customer);
        return ResponseEntity.noContent().build();
    }
}