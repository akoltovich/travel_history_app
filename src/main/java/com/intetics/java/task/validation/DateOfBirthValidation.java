package com.intetics.java.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface DateOfBirthValidation {
    String message() default "Employee must be older than 18 years.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}