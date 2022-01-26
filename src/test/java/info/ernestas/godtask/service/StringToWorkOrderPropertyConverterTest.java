package info.ernestas.godtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import info.ernestas.godtask.model.exception.CanNotProcessRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToWorkOrderPropertyConverterTest {

    private StringToWorkOrderPropertyConverter converter;

    @BeforeEach
    void setUp() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        converter = new StringToWorkOrderPropertyConverter(objectMapper);
    }

    @Test
    void whenCorrectOrderJsonCanBeConvertedToJson_createObject() {
        var validJson = "{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}";

        converter.setAsText(validJson);

        assertNotNull(converter.getValue());
    }

    @Test
    void whenIncorrectOrderJsonCanBeConvertedToJson_createObject() {
        var validJson = "{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"c\":123.12}";

        assertThrows(CanNotProcessRequestException.class, () -> converter.setAsText(validJson));
    }
}
