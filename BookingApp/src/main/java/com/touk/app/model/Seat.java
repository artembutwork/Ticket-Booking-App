package com.touk.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rowNumber;

    @Column(nullable = false)
    private int seatNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Room room;

    @ManyToMany
    @JoinTable(name = "SeatReserved",
            joinColumns = @JoinColumn(name = "seat_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private Set<Reservation> reservations;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "rowNumber = " + rowNumber + ", " +
                "seatNumber = " + seatNumber + ", " +
                "room = " + room + ")";
    }
}
