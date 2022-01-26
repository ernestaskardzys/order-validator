package info.ernestas.godtask.service.validator.constraint;

import info.ernestas.godtask.service.validator.RequireInventoryNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RequireInventoryNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireInventoryNumber {
    String message() default "Inventory number must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
