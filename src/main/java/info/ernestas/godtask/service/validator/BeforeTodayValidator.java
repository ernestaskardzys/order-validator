package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.BeforeToday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BeforeTodayValidator implements ConstraintValidator<BeforeToday, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null || localDate.isAfter(LocalDate.now())) {
            return false;
        }

        return true;
    }
}
