package com.touk.app.service;

import com.touk.app.Common;
import com.touk.app.dto.request.*;
import com.touk.app.dto.response.ReservationMessageDTO;
import com.touk.app.exception.*;
import com.touk.app.helper.LocaleResolver;
import com.touk.app.model.*;
import com.touk.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    private HttpServletRequest request;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              CustomerRepository customerRepository,
                              ScreeningRepository screeningRepository,
                              MessageSource messageSource, LocaleResolver localeResolver) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    public ReservationMessageDTO save(ReservationDTO reservationDTO, HttpServletRequest request){
        this.request = request;

        validateReservationData(reservationDTO);

        Screening screening = getScreening(reservationDTO);
        validateReservationTime(screening);

        Set<Seat> seatsToReserve = getRequestedSeats(screening, reservationDTO);
        validateRequestedSeats(screening,  seatsToReserve);

        createCustomer(reservationDTO);
        long createdCustomerId = this.customerRepository.getLastRecordId();
        Customer customer = this.customerRepository.findById(createdCustomerId).orElse(null);

        double price = getTicketsPrice(reservationDTO);
        LocalDateTime expirationTime = getExpirationDate(screening);

        Reservation reservation = new Reservation(price, expirationTime, customer, screening, seatsToReserve);
        this.reservationRepository.save(reservation);
        return getResponse(price, expirationTime);
    }
    private void validateReservationData(ReservationDTO reservationDTO) {
        validateReservationCustomerData(reservationDTO);
        validateReservationSeatsLimit(reservationDTO);
    }
    private void validateReservationCustomerData(ReservationDTO reservationDTO) {
        String firstName = reservationDTO.getFirstName();
        String lastName = reservationDTO.getLastName();
        if(firstName.trim().length() < 3)
            throw new InvalidRequestDataException("user.nameLengthMessage", this.request);
        if(Character.isLowerCase(firstName.trim().charAt(0)))
            throw new InvalidRequestDataException("user.nameCapitalLetterMessage", this.request);

        if(lastName.trim().length() < 3)
            throw new InvalidRequestDataException("user.surnameLengthMessage", this.request);
        if(Character.isLowerCase(lastName.trim().charAt(0)))
            throw new InvalidRequestDataException("user.surnameCapitalLetterMessage", this.request);
        String[] lastNameParts = lastName.split("-");
        if(lastNameParts.length == 2)
            if(Character.isLowerCase(lastNameParts[1].charAt(0)))
                throw new InvalidRequestDataException("user.surnameSecondPartCapitalLetterMessage", this.request);
    }
    private void validateReservationSeatsLimit(ReservationDTO reservationDTO) {
        List<ReservationSeatDTO> requestedSeats = reservationDTO.getSeats();
        if(requestedSeats == null)
            throw new InvalidRequestDataException("user.atLeastOneSeatInReservationMessage", this.request);

        List<ReservationSeatDTO> notEmptySeats = requestedSeats.stream()
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList());
        if(notEmptySeats.isEmpty())
            throw new InvalidRequestDataException("user.atLeastOneSeatInReservationMessage", this.request);
    }
    private Screening getScreening(ReservationDTO reservationDTO) {
        ScreeningDetailsDTO screeningDetails = reservationDTO.getScreeningDetails();
        if(screeningDetails == null)
            throw new InvalidRequestDataException("user.screeningDataMessage", this.request);

        String movieTitle = screeningDetails.getMovieTitle();
        if(movieTitle == null || movieTitle.trim().isEmpty())
            throw new InvalidRequestDataException("user.screeningTitleMessage", this.request);

        String screeningStartTimeData = screeningDetails.getScreeningStartTime();
        if(screeningStartTimeData == null || screeningStartTimeData.trim().isEmpty())
            throw new InvalidRequestDataException("user.timeFormatMessage", this.request);

        LocalDateTime screeningStartTime;
        try {
            screeningStartTime = LocalDateTime.parse(screeningStartTimeData, Common.dateTimeFormatter);
        } catch (DateTimeParseException e){
            throw new InvalidRequestDataException("user.timeFormatMessage", request);
        }

        Screening screening = this.screeningRepository.findByMovieTitleAndStartTime(
                                                        screeningDetails.getMovieTitle(),
                                                        screeningStartTime);
        if(screening == null)
            throw new InvalidRequestDataException("user.screeningExistenceMessage", this.request);
        return screening;
    }
    private void validateReservationTime(Screening screening) {
        long minutesDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), screening.getStartTime());
        if(minutesDifference < 15)
            throw new InvalidRequestDataException("user.bookingTimeMessage", this.request);
    }
    private Set<Seat> getRequestedSeats(Screening screening, ReservationDTO reservationDTO) {
        return screening.getRoom().getSeats()
                .stream()
                .filter(seat -> {
                    for (ReservationSeatDTO requestedSeatDTO: reservationDTO.getSeats())
                        if(seatEntityEqualsRequestedSeat(seat, requestedSeatDTO))
                            return true;
                    return false;
                })
                .collect(Collectors.toSet());
    }
    private boolean seatEntityEqualsRequestedSeat(Seat seat, ReservationSeatDTO requestedSeatDTO) {
        return seat.getRowNumber() == requestedSeatDTO.getRowNumber() &&
                seat.getSeatNumber() == requestedSeatDTO.getSeatNumber();
    }
    private void validateRequestedSeats(Screening screening, Set<Seat> seatsToReserve) {
        List<Reservation> screeningReservations = this.reservationRepository.findByScreeningId(screening.getId());

        validateAlreadyReservedSeats(screeningReservations, seatsToReserve);
        validateReservedSeatPosition(screeningReservations, seatsToReserve);
    }
    private void validateAlreadyReservedSeats(List<Reservation> screeningReservations, Set<Seat> seatsToReserve) {
        boolean containAlreadyReservedSeat = screeningReservations.stream()
                .anyMatch(reservation ->
                        reservation.getSeats().stream()
                                .anyMatch(seatsToReserve::contains)
                );
        if(containAlreadyReservedSeat)
            throw new InvalidRequestDataException("user.seatAlreadyReservedMessage", this.request);
    }
    private void validateReservedSeatPosition(List<Reservation> screeningReservations, Set<Seat> seatsToReserve) {
        TreeSet<Seat> reservedSeats = new TreeSet<>(Common.seatComparator);
        for (Reservation reservation: screeningReservations)
            reservedSeats.addAll(reservation.getSeats());
        reservedSeats.addAll(seatsToReserve);

        List<List<Seat>> cinemaHall = new ArrayList<>();
        int previousRowIndex = 1;
        List<Seat> row = new ArrayList<>();
        for (Seat seat: reservedSeats){
            if(previousRowIndex != seat.getRowNumber()){
                cinemaHall.add(row);
                row = new ArrayList<>();
            }
            row.add(seat);
            previousRowIndex = seat.getRowNumber();
        }
        cinemaHall.add(row);

        if(!matchesSeatsPositionCondition(cinemaHall))
            throw new InvalidRequestDataException("user.incorrectSeatPositionMessage", this.request);
    }
    private boolean matchesSeatsPositionCondition(List<List<Seat>> cinemaHall){
        for (List<Seat> row: cinemaHall)
            for (int i = 0; i < row.size() - 1; i++)
                if(row.get(i).getSeatNumber() + 2 == row.get(i + 1).getSeatNumber())
                    return false;
        return true;
    }
    private void createCustomer(ReservationDTO reservationDTO) {
        Customer newCustomer = new Customer(reservationDTO.getFirstName(), reservationDTO.getLastName());
        this.customerRepository.save(newCustomer);
    }
    private double getTicketsPrice(ReservationDTO reservationDTO) {
        double sum = 0;
        List<ReservationSeatDTO> requestedSeats = reservationDTO.getSeats();
        for (ReservationSeatDTO requestedSeat : requestedSeats) {
            switch (requestedSeat.getTicketType()) {
                case "adult" : sum += 25; break;
                case "student" : sum += 18; break;
                case "child" : sum += 12.5; break;
            }
        }
        return sum;
    }
    private LocalDateTime getExpirationDate(Screening screening) {
        return screening.getStartTime().minusMinutes(15);
    }
    private ReservationMessageDTO getResponse(double price, LocalDateTime expirationTime) {
        String message =  this.messageSource.getMessage(
                "user.reservationCreatedMessage",
                null,
                this.localeResolver.resolveLocale(this.request));
        return new ReservationMessageDTO(message, price, expirationTime.format(Common.dateTimeFormatter));
    }
}