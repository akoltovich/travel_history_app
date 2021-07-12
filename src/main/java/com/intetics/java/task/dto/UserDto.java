package com.intetics.java.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intetics.java.task.validation.DateOfBirthValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank
    @Length(min = 2, max = 30)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 30)
    private String lastName;

    @NotNull
    @DateOfBirthValidation
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany
    private List<TravelDto> travels;
}
