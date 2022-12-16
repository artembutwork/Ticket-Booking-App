package com.touk.app.dto.response;

import com.touk.app.Common;
import com.touk.app.model.Screening;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ScreeningMoviesDTO {

    private String title;
    private int screeningTime;
    private String startTime;

    public static ScreeningMoviesDTO getInstance(Screening screening) {
        return new ScreeningMoviesDTO(
                screening.getMovie().getTitle(),
                screening.getMovie().getScreeningTime(),
                screening.getStartTime().format(Common.dateTimeFormatter)
        );
    }
}
