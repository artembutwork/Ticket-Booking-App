package com.touk.app.dto.request;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ReservationSeatDTO {

    private int rowNumber;
    private int seatNumber;
    private String ticketType;
}
