package com.touk.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Room room;

    @OneToMany(mappedBy = "screening")
    private Set<Reservation> reservations;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "startTime = " + startTime + ", " +
                "movie = " + movie + ", " +
                "room = " + room + ")";
    }
}
