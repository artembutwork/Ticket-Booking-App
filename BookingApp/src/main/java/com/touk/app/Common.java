package com.touk.app;

import com.touk.app.model.Seat;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Common {

    public static final DateTimeFormatter dateTimeFormatter;
    public static final Comparator<Seat> seatComparator;

    static {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        seatComparator = (firstSeat, secondSeat) -> firstSeat.getRowNumber() != secondSeat.getRowNumber() ?
                                                    firstSeat.getRowNumber() - secondSeat.getRowNumber() :
                                                    firstSeat.getSeatNumber() - secondSeat.getSeatNumber();
    }
}
