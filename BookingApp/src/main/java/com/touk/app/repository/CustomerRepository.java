package com.touk.app.repository;

import com.touk.app.model.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT MAX(c.id) FROM Customer c")
    Long getLastRecordId();
}
