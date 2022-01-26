package info.ernestas.godtask.service.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class Iso4217CurrencyValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private Iso4217CurrencyValidator validator;

    @ParameterizedTest
    @ValueSource(strings = { "USD", "EUR", "ILS", "GBP", "NOK", "DKK" })
    void whenValidCurrencyIsPassed_itIsValid(String currency) {
        assertTrue(validator.isValid(currency, constraintValidatorContext));
    }

    @Test
    void whenNoCurrencyIsPassed_itIsInvalid() {
        assertFalse(validator.isValid(null, constraintValidatorContext));
    }

    @ParameterizedTest
    @ValueSource(strings = { "US", "Euro", "", "British Pound", "XX1", "   " })
    void whenInvalidCurrencyIsPassed_itIsInvalid(String currency) {
        assertFalse(validator.isValid(currency, constraintValidatorContext));
    }
}
