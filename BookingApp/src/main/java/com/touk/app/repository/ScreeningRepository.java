package com.touk.app.repository;

import com.touk.app.model.Screening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s " +
            "FROM Screening s " +
            "WHERE s.startTime BETWEEN :periodStartTime AND :periodEndTime")
    List<Screening> findByStartTime(LocalDateTime periodStartTime, LocalDateTime periodEndTime);

    @Query("SELECT s " +
            "FROM Screening s " +
            "WHERE s.movie.title = :movieTitle AND s.startTime = :startTime")
    Screening findByMovieTitleAndStartTime(String movieTitle, LocalDateTime startTime);
}
