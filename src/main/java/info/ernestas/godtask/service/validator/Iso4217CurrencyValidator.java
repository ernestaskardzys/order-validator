package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.service.validator.constraint.Iso4217Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;

public class Iso4217CurrencyValidator implements ConstraintValidator<Iso4217Currency, String> {

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext cxt) {
        if (currencyCode == null || currencyCode.trim().length() != 3) {
            return false;
        }

        try {
            return Currency.getAvailableCurrencies().contains(Currency.getInstance(currencyCode.trim()));
        } catch (Exception e) {
            return false;
        }
    }

}
