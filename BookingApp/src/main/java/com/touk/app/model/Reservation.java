package com.touk.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "ExpirationTime", nullable = false)
    private LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Screening screening;

    @ManyToMany
    @JoinTable(name = "SeatReserved",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private Set<Seat> seats;

    public Reservation(double price, LocalDateTime expirationTime,
                       Customer customer, Screening screening, Set<Seat> seats) {
        this.price = price;
        this.expirationTime = expirationTime;
        this.customer = customer;
        this.screening = screening;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "price = " + price + ", " +
                "expirationTime = " + expirationTime + ", " +
                "user = " + customer + ", " +
                "screening = " + screening + ")";
    }
}
