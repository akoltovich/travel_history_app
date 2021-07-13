package com.intetics.java.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
@Documented
public @interface CountryValidation {
    String message() default "This country does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
