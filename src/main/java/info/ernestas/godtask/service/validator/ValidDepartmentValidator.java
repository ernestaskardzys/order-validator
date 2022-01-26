package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.config.properties.GodConfigurationProperties;
import info.ernestas.godtask.service.validator.constraint.ValidDepartment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDepartmentValidator implements ConstraintValidator<ValidDepartment, String> {

    private final GodConfigurationProperties properties;

    public ValidDepartmentValidator(GodConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean isValid(String departmentField, ConstraintValidatorContext cxt) {
        return properties.getValidDepartments().contains(departmentField);
    }

}
