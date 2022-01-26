package info.ernestas.godtask.service.validator;

import info.ernestas.godtask.model.orders.Part;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequireInventoryNumberValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    
    @InjectMocks
    private RequireInventoryNumberValidator validator;

    @ParameterizedTest
    @ValueSource(strings = { "123", " ", "LongNumber" })
    void whenInventoryNumberIsNotEmpty_itIsValid(String inventoryNumber) {
        List<Part> parts = getParts(inventoryNumber);
        assertTrue(validator.isValid(parts, constraintValidatorContext));
    }

    @ParameterizedTest
    @ValueSource(strings = { "" })
    void whenInventoryNumberIsEmptyOrNull_itIsNotValid(String inventoryNumber) {
        List<Part> parts = getParts(inventoryNumber);
        assertFalse(validator.isValid(parts, constraintValidatorContext));
    }

    private List<Part> getParts(String inventoryNumber) {
        return List.of(new Part(inventoryNumber, "First item", 10));
    }

}
