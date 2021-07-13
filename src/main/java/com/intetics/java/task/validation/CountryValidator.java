package com.intetics.java.task.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CountryValidator implements ConstraintValidator<CountryValidation, String> {


    @Override
    public void initialize(CountryValidation constraintAnnotation) {
        Locale.setDefault(Locale.ENGLISH);
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] locales = Locale.getISOCountries();
        List<String> countries = new ArrayList<>();
        boolean isValid = false;
        for (String countyCode : locales) {
            Locale locale = new Locale("", countyCode);
            String name = locale.getDisplayName();
            countries.add(name);
        }
        Iterator<String> iterator = countries.iterator();
        while (iterator.hasNext() && !isValid) {
            isValid = value.equalsIgnoreCase(iterator.next());
        }
        return isValid;
    }
}
