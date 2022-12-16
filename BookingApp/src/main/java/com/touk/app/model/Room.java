package com.touk.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "room")
    private Set<Screening> screenings;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "capacity = " + capacity + ")";
    }
}
