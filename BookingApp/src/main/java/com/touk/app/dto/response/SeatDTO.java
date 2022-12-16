package com.touk.app.dto.response;

import com.touk.app.model.Seat;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class SeatDTO {

    private int rowNumber;
    private int seatNumber;

    public static SeatDTO getInstance(Seat seat) {
        return new SeatDTO(
                seat.getRowNumber(),
                seat.getSeatNumber()
        );
    }
}
