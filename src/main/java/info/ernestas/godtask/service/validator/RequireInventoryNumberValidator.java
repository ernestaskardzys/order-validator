package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.model.orders.Part;
import info.ernestas.godtask.service.validator.constraint.RequireInventoryNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RequireInventoryNumberValidator implements ConstraintValidator<RequireInventoryNumber, List<Part>> {

    @Override
    public boolean isValid(List<Part> parts, ConstraintValidatorContext constraintValidatorContext) {
        return parts.stream().noneMatch(part -> part.getInventoryNumber() == null || part.getInventoryNumber().isEmpty());
    }
}
