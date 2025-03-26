package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.TicketExpense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TicketExpenseRepository extends JpaRepository<TicketExpense, Integer> {
    @Query(value = "SELECT * FROM ticket_expense WHERE ticket_histo_id = :idTicketHisto ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<TicketExpense> findByIdHistoDateMax(@Param("idTicketHisto") int idTicketHisto);

    @Query("""
    SELECT COALESCE(SUM(e.amount), 0.0) FROM TicketExpense e
    WHERE e.createdAt = (
        SELECT MAX(e2.createdAt) FROM TicketExpense e2
        WHERE e2.ticketHisto.id = e.ticketHisto.id
    )
    AND e.ticketHisto IN (
        SELECT t FROM TicketHisto t
        WHERE (t.deleteAt IS NULL OR t.deleteAt >= :startDate)
        AND t.createdAt <= :endDate
    )
""")
    BigDecimal getTotalLatestExpenseBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);



}
