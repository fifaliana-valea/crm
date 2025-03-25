package site.easy.to.build.crm.repository;

import site.easy.to.build.crm.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TriggerLeadHistoRepository extends JpaRepository<TriggerLeadHisto, Long> {
    @Query("SELECT t FROM TriggerLeadHisto t " +
            "WHERE (t.createdAt <= COALESCE(:date2, CURRENT_TIMESTAMP)) " +
            "AND (t.deleteAt IS NULL OR t.deleteAt >= COALESCE(:date1, t.createdAt)) " +
            "AND (:date1 IS NULL OR :date2 IS NULL OR t.createdAt <= :date2) " +
            "AND (:date1 IS NULL OR :date2 IS NULL OR t.deleteAt IS NULL OR t.deleteAt >= :date1)")
    List<TriggerLeadHisto> getBetweenDate(
            @Param("date1") LocalDateTime date1,
            @Param("date2") LocalDateTime date2);

    List<TriggerLeadHisto> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<TriggerLeadHisto> findByDeleteAtIsNull();

    TriggerLeadHisto findByIdAndDeleteAtIsNull(Integer id);

    @Modifying
    @Query("UPDATE TriggerLeadHisto t SET t.deleteAt = :now WHERE t.id = :id")
    void updateDeletedAt(@Param("id") Integer id, @Param("now") LocalDateTime now);

    List<TriggerLeadHisto> findByCustomerCustomerId(int idCustomer);
    // Méthode pratique qui utilise directement LocalDateTime.now()
    default void markAsDeletedNow(Integer id) {
        updateDeletedAt(id, LocalDateTime.now());
    }
}