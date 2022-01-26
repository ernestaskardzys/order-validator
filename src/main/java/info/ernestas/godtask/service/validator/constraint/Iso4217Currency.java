package info.ernestas.godtask.service.validator.constraint;

import info.ernestas.godtask.service.validator.Iso4217CurrencyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = Iso4217CurrencyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Iso4217Currency {

    String message() default "Invalid currency name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
