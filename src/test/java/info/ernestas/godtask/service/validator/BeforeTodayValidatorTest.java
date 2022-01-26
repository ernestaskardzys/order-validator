package info.ernestas.godtask.service.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeforeTodayValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private BeforeTodayValidator validator;

    @Test
    void whenDateIsBeforeToday_itIsValid() {
        assertTrue(validator.isValid(LocalDate.now().minusDays(1), constraintValidatorContext));
        assertTrue(validator.isValid(LocalDate.now().minusMonths(1), constraintValidatorContext));
        assertTrue(validator.isValid(LocalDate.now().minusWeeks(1), constraintValidatorContext));
    }

    @Test
    void whenDateIsTodayOrAfterToday_itIsValid() {
        assertFalse(validator.isValid(LocalDate.now().plusDays(1), constraintValidatorContext));
        assertFalse(validator.isValid(LocalDate.now().plusMonths(1), constraintValidatorContext));
        assertFalse(validator.isValid(LocalDate.now().plusWeeks(1), constraintValidatorContext));
    }

    @Test
    void whenDateIsNull_itIsNotValid() {
        assertFalse(validator.isValid(LocalDate.now().plusDays(1), constraintValidatorContext));
        assertFalse(validator.isValid(LocalDate.now().plusMonths(1), constraintValidatorContext));
        assertFalse(validator.isValid(LocalDate.now().plusWeeks(1), constraintValidatorContext));
    }
}
