package info.ernestas.godtask.service.validator.constraint;

import info.ernestas.godtask.service.validator.AfterDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AfterDateValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterDate {
    String message() default "Incorrect date - must be not empty and after specified date";

    String baseField();

    String startDateField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
