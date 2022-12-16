package com.touk.app.dto.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ReservationMessageDTO {

    private String message;
    private double totalPrice;
    private String expirationTime;
}
