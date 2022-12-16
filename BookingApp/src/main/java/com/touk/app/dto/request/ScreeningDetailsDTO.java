package com.touk.app.dto.request;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ScreeningDetailsDTO {

    private String movieTitle;
    private String screeningStartTime;
}
