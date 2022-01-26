package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.AfterDateAndBeforeDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AfterDateAndBeforeDateValidatorTest {

    private static final LocalDate START_DATE = LocalDate.of(2022, 1, 25);
    private static final LocalDate END_DATE = LocalDate.of(2022, 1, 27);
    private static final LocalDate TARGET_DATE = LocalDate.of(2022, 1, 26);
    private static final String TARGET_DATE_FIELD_NAME = "analysisDate";
    private static final String START_DATE_FIELD_NAME = "startDate";
    private static final String END_DATE_FIELD_NAME = "endDate";

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private AfterDateAndBeforeDate constraint;

    private AfterDateAndBeforeDateValidator validator;

    @BeforeEach
    void setUp() {
        when(constraint.baseField()).thenReturn(TARGET_DATE_FIELD_NAME);
        when(constraint.startDateField()).thenReturn(START_DATE_FIELD_NAME);
        when(constraint.endDateField()).thenReturn(END_DATE_FIELD_NAME);
    }

    @Test
    void whenTargetDateIsAfterStartDateAndBeforeEndDate_itIsValid() {
        validator = new FakeAfterDateAndBeforeDateValidator(START_DATE, END_DATE, TARGET_DATE);
        validator.initialize(constraint);

        assertTrue(validator.isValid(TARGET_DATE, constraintValidatorContext));
    }

    @Test
    void whenTargetDateIsBeforeStartDate_itIsInvalid() {
        var targetDate = LocalDate.of(2020, 1, 1);
        validator = new FakeAfterDateAndBeforeDateValidator(START_DATE, END_DATE, targetDate);
        validator.initialize(constraint);

        assertFalse(validator.isValid(targetDate, constraintValidatorContext));
    }

    @Test
    void whenTargetDateIsAfterEndDate_itIsInvalid() {
        var targetDate = LocalDate.of(2023, 1, 1);
        validator = new FakeAfterDateAndBeforeDateValidator(START_DATE, END_DATE, targetDate);
        validator.initialize(constraint);

        assertFalse(validator.isValid(targetDate, constraintValidatorContext));
    }

    @Test
    void whenTargetDateIsEqualToEndDate_itIsInvalid() {
        validator = new FakeAfterDateAndBeforeDateValidator(START_DATE, END_DATE, END_DATE);
        validator.initialize(constraint);

        assertFalse(validator.isValid(END_DATE, constraintValidatorContext));
    }

    @Test
    void whenTargetDateIsEqualToStartEndDate_itIsInvalid() {
        validator = new FakeAfterDateAndBeforeDateValidator(START_DATE, END_DATE, START_DATE);
        validator.initialize(constraint);

        assertFalse(validator.isValid(START_DATE, constraintValidatorContext));
    }

    private static class FakeAfterDateAndBeforeDateValidator extends AfterDateAndBeforeDateValidator {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalDate analysisDate;

        public FakeAfterDateAndBeforeDateValidator(LocalDate startDate, LocalDate endDate, LocalDate analysisDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.analysisDate = analysisDate;
        }

        @Override
        protected LocalDate getFieldValue(Object value, String field) {
            if (field.equals(START_DATE_FIELD_NAME)) {
                return startDate;
            } else if (field.equals(END_DATE_FIELD_NAME)) {
                return endDate;
            }
            return analysisDate;
        }
    }

}
