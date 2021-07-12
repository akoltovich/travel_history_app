package com.intetics.java.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "travel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    @NotBlank
    @Length(min = 2, max = 30)
    private String country;

    @Column(name = "year_of_travel")
    @NotNull
    private int yearOfTravel;

    @Column(name = "weather")
    @NotBlank
    @Length(min = 2, max = 30)
    private String weather;

    @Column(name = "description")
    @NotBlank
    @Length(min = 2, max = 30)
    private String description;

    @ManyToOne
    private User user;

}
