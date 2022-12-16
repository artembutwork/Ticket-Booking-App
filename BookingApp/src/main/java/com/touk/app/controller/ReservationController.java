package com.touk.app.controller;

import com.touk.app.dto.request.ReservationDTO;
import com.touk.app.dto.response.ReservationMessageDTO;
import com.touk.app.exception.InvalidRequestDataException;
import com.touk.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;


    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations/create")
    public ResponseEntity<ReservationMessageDTO> createReservation(
            @RequestBody ReservationDTO reservation,
            HttpServletRequest request) {
        if(reservation == null)
            throw new InvalidRequestDataException("user.reservationDataMessage", request);
        return ResponseEntity.ok(this.reservationService.save(reservation, request));
    }
}
