package info.ernestas.godtask.service.validator.constraint;

import info.ernestas.godtask.service.validator.AfterDateAndBeforeDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AfterDateAndBeforeDateValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AfterDateAndBeforeDateConstraints.class)
public @interface AfterDateAndBeforeDate {
    String message() default "Incorrect date - must be after start date and before end date date";

    String baseField();

    String startDateField();

    String endDateField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
