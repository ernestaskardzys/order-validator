package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.AfterDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AfterDateValidatorTest {

    private static final LocalDate START_DATE = LocalDate.of(2022, 1, 25);
    private static final LocalDate END_DATE = LocalDate.of(2022, 1, 27);
    private static final String START_DATE_FIELD_NAME = "startDate";
    private static final String END_DATE_FIELD_NAME = "endDate";

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private AfterDate constraint;

    private AfterDateValidator validator;

    @BeforeEach
    void setUp() {
        when(constraint.baseField()).thenReturn(END_DATE_FIELD_NAME);
        when(constraint.startDateField()).thenReturn(START_DATE_FIELD_NAME);
    }

    @Test
    void whenEndDateIsAfterStartDate_itIsAValidDate() {
        validator = new FakeAfterDateValidator(START_DATE, END_DATE);
        validator.initialize(constraint);

        assertTrue(validator.isValid(END_DATE, constraintValidatorContext));
    }

    @Test
    void whenEndDateIsEmpty_itIsInvalidDate() {
        validator = new FakeAfterDateValidator(START_DATE, null);
        validator.initialize(constraint);

        assertFalse(validator.isValid(null, constraintValidatorContext));
    }

    private static class FakeAfterDateValidator extends AfterDateValidator {

        private final LocalDate startDate;
        private final LocalDate endDate;

        public FakeAfterDateValidator(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        protected LocalDate getFieldValue(Object value, String field) {
            if (field.equals(START_DATE_FIELD_NAME)) {
                return startDate;
            }
            return endDate;
        }

    }

}
