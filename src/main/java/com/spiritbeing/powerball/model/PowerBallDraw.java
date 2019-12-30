package com.spiritbeing.powerball.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @ToString
public class PowerBallDraw {
    public static final String ball = " Ball ";
    public static final String message1 = " value cannot be less than one";
    public static final String message2 = " value cannot be greater than 69";
    public static final String message3 = " value cannot be greater than 26";

    private Long id;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = ball + 1 + message1)
    @Max(value = 69, message = ball + 1 + message2)
    private Integer ball_1;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = ball + 2 + message1)
    @Max(value = 69, message = ball + 2 + message2)
    private Integer ball_2;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = ball + 3 + message1)
    @Max(value = 69, message = ball + 3 + message2)
    private Integer ball_3;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = ball + 4 + message1)
    @Max(value = 69, message = ball + 4 + message2)
    private Integer ball_4;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = ball + 5 + message1)
    @Max(value = 69, message = ball + 5 + message2)
    private Integer ball_5;

    @NotNull(message = "cannot be empty")
    @Min(value = 1, message = "Red " + ball +  message1)
    @Max(value = 26, message = "Red " + ball + message3)
    private Integer ball_6;

//    @Past
//    @Future
//    @FutureOrPresent
    @PastOrPresent
    @NotNull(message = "choose a valid date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;  // since this give string  and convert wen saving
}
