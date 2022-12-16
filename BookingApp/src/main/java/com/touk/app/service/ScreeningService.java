package com.touk.app.service;

import com.touk.app.Common;
import com.touk.app.dto.request.ScreeningDetailsDTO;
import com.touk.app.dto.response.*;
import com.touk.app.exception.InvalidRequestDataException;
import com.touk.app.model.Screening;
import com.touk.app.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }
    public List<ScreeningMoviesDTO> getAvailableScreenings(
            String periodStartTime,
            String periodEndTime,
            HttpServletRequest request) {
        LocalDateTime periodStartValue, periodEndValue;
        try {
            periodStartValue = LocalDateTime.parse(periodStartTime, Common.dateTimeFormatter);
            periodEndValue = LocalDateTime.parse(periodEndTime, Common.dateTimeFormatter);
        } catch (DateTimeParseException e){
            throw new InvalidRequestDataException("user.timeFormatMessage", request);
        }

        if(periodStartValue.isAfter(periodEndValue))
            throw new InvalidRequestDataException("user.periodFormatMessage", request);

        Comparator<ScreeningMoviesDTO> resultComparator = (firstResult, secondResult) -> {
            if(!firstResult.getTitle().equals(secondResult.getTitle()))
                return firstResult.getTitle().compareTo(secondResult.getTitle());
            else
                return firstResult.getScreeningTime() - secondResult.getScreeningTime();
        };
        return this.screeningRepository.findByStartTime(periodStartValue, periodEndValue)
                .stream()
                .map(ScreeningMoviesDTO::getInstance)
                .sorted(resultComparator)
                .collect(Collectors.toList());
    }
    public ScreeningInfoDTO getScreeningByMovieTitleAndStartTime(
            ScreeningDetailsDTO screeningDetails,
            HttpServletRequest request) {
        LocalDateTime screeningStartTime;
        try {
            screeningStartTime = LocalDateTime.parse(screeningDetails.getScreeningStartTime(), Common.dateTimeFormatter);
        } catch (DateTimeParseException e){
            throw new InvalidRequestDataException("user.timeFormatMessage", request);
        }

        Screening screening = this.screeningRepository
                .findByMovieTitleAndStartTime(screeningDetails.getMovieTitle(), screeningStartTime);
        if(screening == null)
            throw new InvalidRequestDataException("user.screeningExistenceMessage", request);
        return ScreeningInfoDTO.getInstance(screening);
    }
}
