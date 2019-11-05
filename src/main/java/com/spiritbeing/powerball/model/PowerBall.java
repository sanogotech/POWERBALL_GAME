package com.spiritbeing.powerball.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class PowerBall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ball_1;
    private Integer ball_2;
    private Integer ball_3;
    private Integer ball_4;
    private Integer ball_5;
    private Integer ball_6;

    @NotNull @Temporal(TemporalType.DATE)
    private Date createdDate;
}
