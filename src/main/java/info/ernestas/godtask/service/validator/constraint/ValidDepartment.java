package info.ernestas.godtask.service.validator.constraint;

import info.ernestas.godtask.service.validator.ValidDepartmentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidDepartmentValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDepartment {
    String message() default "Invalid department name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
