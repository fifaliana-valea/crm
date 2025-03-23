package site.easy.to.build.crm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.Customer;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findByCustomerId(int customerId);

    public List<Customer> findByUserId(int userId);

    public Customer findByEmail(String email);

    public List<Customer> findAll();

    public List<Customer> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);

    long countByUserId(int userId);

    @Query("SELECT c FROM Customer c " +
            "WHERE (:date1 IS NULL OR c.createdAt >= :date1) " +
            "AND (:date2 IS NULL OR c.createdAt <= :date2)")
    List<Customer> getBetweenDate(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);
}
