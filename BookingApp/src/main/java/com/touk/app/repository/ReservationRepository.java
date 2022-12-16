package com.touk.app.repository;

import com.touk.app.model.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r " +
            "FROM Reservation r " +
            "WHERE r.screening.id = :screeningId")
    List<Reservation> findByScreeningId(Long screeningId);
}
