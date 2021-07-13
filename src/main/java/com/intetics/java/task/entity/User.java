package com.intetics.java.task.entity;

import com.intetics.java.task.validation.DateOfBirthValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    @Length(min = 2, max = 30)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Length(min = 2, max = 30)
    private String lastName;

    @Column(name = "date_of_birth")
    @NotNull
    @DateOfBirthValidation
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Travel> travels;
}
