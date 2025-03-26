package site.easy.to.build.crm.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import site.easy.to.build.crm.entity.LeadExpense;

@Repository
public interface LeadExpenseRepository extends JpaRepository<LeadExpense, Long> {

    @Query(value = "SELECT * FROM lead_expense WHERE trigger_lead_histo_id = :triggerLeadHistoId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    LeadExpense findLatestByTriggerLeadHistoId(@Param("triggerLeadHistoId") Integer triggerLeadHistoId);

    Optional<LeadExpense> findById(Integer id);

    @Query("""
    SELECT COALESCE(SUM(e.amount), 0.0) FROM TicketExpense e
    WHERE e.id IN (
        SELECT MAX(e2.id) FROM TicketExpense e2
        WHERE e2.ticketHisto.id = e.ticketHisto.id
        GROUP BY e2.ticketHisto.id
    )
    AND e.ticketHisto IN (
        SELECT t FROM TicketHisto t
        WHERE (:startDate IS NULL OR t.createdAt >= :startDate)
        AND (:endDate IS NULL OR t.createdAt <= :endDate)
        AND (t.deleteAt IS NULL)
    )
""")
    BigDecimal getTotalLatestExpenseBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
