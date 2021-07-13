package com.intetics.java.task.dto;

import com.intetics.java.task.validation.CountryValidation;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelDto {

    private Long id;

    @CountryValidation
    @NotBlank
    @Length(min = 2, max = 30)
    private String country;

    @NotNull
    private int yearOfTravel;

    @Setter(AccessLevel.NONE)
    private String weather;

    @NotBlank
    @Length(min = 2, max = 30)
    private String description;

    @NotNull
    private Long userId;
}
