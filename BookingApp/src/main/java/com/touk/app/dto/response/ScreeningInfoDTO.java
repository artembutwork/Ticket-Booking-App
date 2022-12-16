package com.touk.app.dto.response;

import com.touk.app.Common;
import com.touk.app.model.Screening;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ScreeningInfoDTO {

    private String roomName;
    private List<SeatDTO> availableSeats;

    public static ScreeningInfoDTO getInstance(Screening screening) {
        return new ScreeningInfoDTO(
                screening.getRoom().getName(),
                getAvailableSeats(screening)
        );
    }

    private static List<SeatDTO> getAvailableSeats(Screening screening) {
        return screening.getRoom().getSeats()
                .stream()
                .filter(seat -> seat.getReservations().isEmpty())
                .sorted(Common.seatComparator)
                .map(SeatDTO::getInstance)
                .collect(Collectors.toList());
    }
}
