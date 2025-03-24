package site.easy.to.build.crm.service.ticket;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketExpense;
import site.easy.to.build.crm.repository.TicketExpenseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public TicketExpense getLatestExpenseForTicketHisto(int ticketHistoId) {
        Optional<TicketExpense> latestExpense = ticketExpenseRepository.findByIdHistoDateMax(ticketHistoId);
        return latestExpense.orElse(null);
    }
}
