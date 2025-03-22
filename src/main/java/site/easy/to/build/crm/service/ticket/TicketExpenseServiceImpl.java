package site.easy.to.build.crm.service.ticket;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.TicketExpense;
import site.easy.to.build.crm.repository.TicketExpenseRepository;

@Service
public class TicketExpenseServiceImpl implements TicketExpenseService{
    private final TicketExpenseRepository ticketExpenseRepository;


    public TicketExpenseServiceImpl(TicketExpenseRepository ticketExpenseRepository) {
        this.ticketExpenseRepository = ticketExpenseRepository;
    }

    @Override
    public TicketExpense save(TicketExpense ticketExpense){
        return ticketExpenseRepository.save(ticketExpense);
    }
}
