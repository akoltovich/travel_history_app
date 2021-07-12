package com.intetics.java.task.dto;

import com.intetics.java.task.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelDto {

    private Long id;

    @NotBlank
    @Length(min = 2, max = 30)
    private String country;

    @NotNull
    private int yearOfTravel;

    @NotBlank
    @Length(min = 2, max = 30)
    private String weather;

    @NotBlank
    @Length(min = 2, max = 30)
    private String description;

    @NotNull
    private Long userId;
}
