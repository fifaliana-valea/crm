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
    // @Query("SELECT tlh FROM TriggerLeadHisto tlh WHERE tlh.createdAt BETWEEN :startDate AND :endDate")
    // List<TriggerLeadHisto> findBetweenDate(
    //     @Param("startDate") LocalDateTime startDate,
    //     @Param("endDate") LocalDateTime endDate
    // );

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