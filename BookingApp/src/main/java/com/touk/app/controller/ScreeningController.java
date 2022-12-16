package com.touk.app.controller;

import com.touk.app.dto.request.ScreeningDetailsDTO;
import com.touk.app.dto.response.*;
import com.touk.app.exception.InvalidRequestDataException;
import com.touk.app.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ScreeningController {

    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<ScreeningMoviesDTO>> getAvailableScreenings(
            @RequestParam String periodStartTime,
            @RequestParam String periodEndTime,
            HttpServletRequest request){
        if(isInvalidRequestParameter(periodStartTime) || isInvalidRequestParameter(periodEndTime))
            throw new InvalidRequestDataException("user.timeFormatMessage", request);
        List<ScreeningMoviesDTO> result = this.screeningService.getAvailableScreenings(periodStartTime, periodEndTime, request);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/screening")
    public ResponseEntity<ScreeningInfoDTO> getScreeningByScreenDetails(
            @RequestBody ScreeningDetailsDTO screeningDetails,
            HttpServletRequest request) {
        if(screeningDetails == null || isInvalidRequestParameter(screeningDetails.getMovieTitle()))
            throw new InvalidRequestDataException("user.screeningDataMessage", request);
        if(isInvalidRequestParameter(screeningDetails.getScreeningStartTime()))
            throw new InvalidRequestDataException("user.timeFormatMessage", request);
        return ResponseEntity.ok(this.screeningService.getScreeningByMovieTitleAndStartTime(screeningDetails, request));
    }
    private boolean isInvalidRequestParameter(String parameter) {
        return parameter == null || parameter.trim().isEmpty();
    }
}
