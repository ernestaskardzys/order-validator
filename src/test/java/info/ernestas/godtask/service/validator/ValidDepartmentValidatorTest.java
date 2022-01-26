package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.config.properties.GodConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidDepartmentValidatorTest {

    private static final List<String> validDepartments = List.of(
        "GOoD analysis department", "GOoD repair department", "GOoD replacement department"
    );

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private GodConfigurationProperties properties;

    @InjectMocks
    private ValidDepartmentValidator validator;

    @BeforeEach
    void setUp() {
        when(properties.getValidDepartments()).thenReturn(validDepartments);
    }

    @ParameterizedTest
    @ValueSource(strings = { "GOoD analysis department", "GOoD repair department", "GOoD replacement department" })
    void whenCorrectDepartmentNamePassed_itIsValid(String departmentName) {
        assertTrue(validator.isValid(departmentName, constraintValidatorContext));
    }

    @ParameterizedTest
    @ValueSource(strings = { "GOoD analysis", "GOoD repair department ", "Not existing department" })
    void whenInCorrectDepartmentNamePassed_itIsNotValid(String departmentName) {
        assertFalse(validator.isValid(departmentName, constraintValidatorContext));
    }
}
