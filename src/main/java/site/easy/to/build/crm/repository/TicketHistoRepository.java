package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketHisto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketHistoRepository extends JpaRepository<TicketHisto, Integer> {
    public TicketHisto findById(int ticketId);

    @Query("SELECT t FROM TicketHisto t " +
            "WHERE (:date1 IS NULL OR t.createdAt >= :date1) " +
            "AND (:date2 IS NULL OR t.createdAt <= :date2)")
    List<TicketHisto> getBetweenDate(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);
}
