package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.AfterDateAndBeforeDate;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterDateAndBeforeDateValidator implements ConstraintValidator<AfterDateAndBeforeDate, Object> {

    private String baseField;
    private String startDate;
    private String endDate;

    @Override
    public void initialize(AfterDateAndBeforeDate constraintAnnotation) {
        this.baseField = constraintAnnotation.baseField();
        this.startDate = constraintAnnotation.startDateField();
        this.endDate = constraintAnnotation.endDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var baseFieldValue = getFieldValue(value, baseField);
        if (baseFieldValue == null) {
            return false;
        }

        var startDateValue = getFieldValue(value, startDate);
        var endDateValue = getFieldValue(value, endDate);

        return baseFieldValue.isAfter(startDateValue) && baseFieldValue.isBefore(endDateValue);
    }

    protected LocalDate getFieldValue(Object value, String field) {
        return (LocalDate) new BeanWrapperImpl(value).getPropertyValue(field);
    }
}
