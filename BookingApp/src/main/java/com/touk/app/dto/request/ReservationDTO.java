package com.touk.app.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ReservationDTO {

    private String firstName;
    private String lastName;
    private ScreeningDetailsDTO screeningDetails;
    List<ReservationSeatDTO> seats;
}
