package info.ernestas.godtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.model.ValidationRequestStatus;
import info.ernestas.godtask.model.response.ValidationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ValidationControllerTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validateAnalysisOrder() throws Exception {
        var response = makeRequest("{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                                    "\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                                    "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}");

        assertValidResponse(response, "GOoD analysis department");
    }

    @Test
    void validateRepairOrder() throws Exception {
        var response = makeRequest("{\"type\":\"REPAIR\",\"department\":\"GOoD repair department\",\"start_date\":\"2020-08-13\"," +
                                        "\"end_date\":\"2020-08-16\",\"analysis_date\":\"2020-08-14\",\"test_date\":\"2020-08-15\"," +
                                        "\"responsible_person\":\"GOoD repair master\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber3\"," +
                                        "\"name\":\"PartNumber3\",\"count\":3},{\"inventory_number\":\"InventoryNumber4\",\"name\":\"PartNumber4\",\"count\":4}]}");

        assertValidResponse(response, "GOoD repair department");
    }

    @Test
    void validateReplacementOrder() throws Exception {
        var response = makeRequest("{\"type\":\"REPLACEMENT\",\"department\":\"GOoD replacement department\"," +
                                        "\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-16\",\"factory_name\":\"GOoDfactory\"," +
                                        "\"factory_order_number\":\"DE12345678\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber5\",\"name\":\"PartNumber5\",\"count\":5},\n" +
                                        "{\"inventory_number\":\"InventoryNumber6\",\"name\":\"PartNumber6\",\"count\":6}]}");

        assertValidResponse(response, "GOoD replacement department");
    }

    @Test
    void validateDestroyingOrder() throws Exception {
        var response = makeRequest("{\"type\":\"DESTROYING\",\"department\":\"GOoD destroying department\",\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12, \"action\": \"boom!\"}");

        assertValidResponse(response, "GOoD destroying department");
    }

    @Test
    void whenIncorrectActionForDestroyingOrderProvided_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"DESTROYING\",\"department\":\"GOoD destroying department\",\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12, \"action\": \"\"}");

        assertInvalidResponse(response, "action: must not be empty", "GOoD destroying department");
    }

    @Test
    void whenIncorrectDepartmentIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"ANALYSIS\",\"department\":\"Fake department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-14\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}");

        assertInvalidResponse(response, "department: Invalid department name", "Fake department");
    }

    @Test
    void whenIncorrectStartDateIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var tomorrow = LocalDate.now().plusDays(1);
        var dayAfterTomorrow = tomorrow.plusDays(1);
        var response = makeRequest(String.format("{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"%s\"," +
                "\"end_date\":\"%s\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}", tomorrow, dayAfterTomorrow));

        assertInvalidResponse(response, "startDate: Incorrect date - must be not empty and before today", "GOoD analysis department");
    }

    @Test
    void whenIncorrectEndDateIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-10\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}");

        assertInvalidResponse(response, "Incorrect end date - must be not empty and after specified date", "GOoD analysis department");
    }

    @Test
    void whenIncorrectCurrencyIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-14\",\"currency\":\"USDX\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}");

        assertInvalidResponse(response, "currency: Invalid currency name", "GOoD analysis department");
    }

    @Test
    void whenIncorrectCostIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-14\",\"currency\":\"USD\",\"cost\":0,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}");

        assertInvalidResponse(response, "cost: must be greater than 0", "GOoD analysis department");
    }

    @Test
    void whenIncorrectAnalysisAndTestDateIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPAIR\",\"department\":\"GOoD repair department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-20\",\"analysis_date\":\"2020-08-20\",\"test_date\":\"2020-08-16\"," +
                "\"responsible_person\":\"GOoD repair master\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber3\"," +
                "\"name\":\"PartNumber3\",\"count\":3},{\"inventory_number\":\"InventoryNumber4\",\"name\":\"PartNumber4\",\"count\":4}]}");

        assertInvalidResponse(response, "Incorrect test date - must be not empty, after analysis date and before end date", "GOoD repair department");
        assertInvalidResponse(response, "Incorrect analysis date - must be after start date and before end date", "GOoD repair department");
    }

    @Test
    void whenIncorrectResponsibleIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPAIR\",\"department\":\"GOoD repair department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-20\",\"analysis_date\":\"2020-08-15\",\"test_date\":\"2020-08-16\"," +
                "\"responsible_person\":\"\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber3\"," +
                "\"name\":\"PartNumber3\",\"count\":3},{\"inventory_number\":\"InventoryNumber4\",\"name\":\"PartNumber4\",\"count\":4}]}");

        assertInvalidResponse(response, "responsiblePerson: must not be empty", "GOoD repair department");
    }

    @Test
    void whenPartsAreNotPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPAIR\",\"department\":\"GOoD repair department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-20\",\"analysis_date\":\"2020-08-15\",\"test_date\":\"2020-08-16\"," +
                "\"responsible_person\":\"Me\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[]}");

        assertInvalidResponse(response, "parts: Must be not empty", "GOoD repair department");
    }

    @Test
    void whenIncorrectFactoryNameIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPLACEMENT\",\"department\":\"GOoD replacement department\"," +
                "\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-16\",\"factory_name\":\"\"," +
                "\"factory_order_number\":\"DE12345678\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber5\",\"name\":\"PartNumber5\",\"count\":5},\n" +
                "{\"inventory_number\":\"InventoryNumber6\",\"name\":\"PartNumber6\",\"count\":6}]}");

        assertInvalidResponse(response, "factoryName: must not be empty", "GOoD replacement department");
    }

    @Test
    void whenIncorrectFactoryOrderNumberIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPLACEMENT\",\"department\":\"GOoD replacement department\"," +
                "\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-16\",\"factory_name\":\"Me\"," +
                "\"factory_order_number\":\"DE1234567890\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber5\",\"name\":\"PartNumber5\",\"count\":5},\n" +
                "{\"inventory_number\":\"InventoryNumber6\",\"name\":\"PartNumber6\",\"count\":6}]}");

        assertInvalidResponse(response, "factoryOrderNumber: must match \"[A-Za-z]{2}[0-9]{8}$\"", "GOoD replacement department");
    }

    @Test
    void whenInventoryNumberIsNotEmptyNumberIsPassed_weShouldGetItInListOfErrors() throws Exception {
        var response = makeRequest("{\"type\":\"REPLACEMENT\",\"department\":\"GOoD replacement department\"," +
                "\"start_date\":\"2020-08-13\",\"end_date\":\"2020-08-16\",\"factory_name\":\"Me\"," +
                "\"factory_order_number\":\"DE12345678\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber5\",\"name\":\"PartNumber5\",\"count\":5},\n" +
                "{\"inventory_number\":\"\",\"name\":\"PartNumber6\",\"count\":6}]}");

        assertInvalidResponse(response, "parts: Inventory number must not be empty", "GOoD replacement department");
    }

    @Test
    void whenInvalidJsonMessageIsSent_returnBadRequestMessageToTheClient() throws Exception {
        mockMvc.perform(
                        post("/api/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"type\":\"REPLACEMENT\", NOT A PROPER JSON HERE}"))
                .andExpect(status().isBadRequest());
    }

    private void assertValidResponse(ValidationResponse response, String department) {
        assertEquals(response.getStatus(), ValidationRequestStatus.VALID);
        assertThat(response.getRequest().length(), greaterThan(0));
        assertThat(response.getErrorMessages(), is(empty()));

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(1));
        var validationRequest = validationRequests.get(0);
        assertThat(validationRequest.getStatus(), is(ValidationRequestStatus.VALID));
        assertThat(validationRequest.getDate(), is(LocalDate.now()));
        assertThat(validationRequest.getDepartment(), is(department));
    }

    private void assertInvalidResponse(ValidationResponse response, String text, String department) {
        assertEquals(response.getStatus(), ValidationRequestStatus.NOT_VALID);
        assertThat(response.getRequest().length(), greaterThan(0));
        assertTrue(response.getErrorMessages().contains(text));

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(1));
        var validationRequest = validationRequests.get(0);
        assertThat(validationRequest.getStatus(), is(ValidationRequestStatus.NOT_VALID));
        assertThat(validationRequest.getDate(), is(LocalDate.now()));
        assertThat(validationRequest.getDepartment(), is(department));
    }

    private ValidationResponse makeRequest(String request) throws Exception {
        var json = mockMvc.perform(
                        post("/api/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(json, ValidationResponse.class);
    }

}
