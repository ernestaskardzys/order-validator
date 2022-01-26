package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.AfterDate;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterDateValidator implements ConstraintValidator<AfterDate, Object> {

    private String baseField;
    private String startDateField;

    @Override
    public void initialize(AfterDate constraintAnnotation) {
        this.baseField = constraintAnnotation.baseField();
        this.startDateField = constraintAnnotation.startDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var baseFieldValue = getFieldValue(value, baseField);
        if (baseFieldValue == null) {
            return false;
        }

        var otherFieldValue = getFieldValue(value, startDateField);

        return baseFieldValue.isAfter(otherFieldValue);
    }

    protected LocalDate getFieldValue(Object value, String field) {
        return (LocalDate) new BeanWrapperImpl(value).getPropertyValue(field);
    }
}
