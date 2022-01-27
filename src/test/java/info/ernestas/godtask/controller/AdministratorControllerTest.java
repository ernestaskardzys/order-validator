package info.ernestas.godtask.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import info.ernestas.godtask.entity.ValidationRequest;
import info.ernestas.godtask.model.ValidationRequestStatus;
import info.ernestas.godtask.model.orders.OrderType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AdministratorControllerTest extends BaseTest {

    @Test
    void validate() throws Exception {
        mockMvc.perform(get("/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("validate"))
                .andExpect(model().attribute("request", equalTo(null)))
                .andExpect(model().attribute("status", equalTo(null)));
    }

    @Test
    void whenSubmittingValidOrder_validationShouldSucceed() throws Exception {
        var validRequest = "{\"type\":\"ANALYSIS\",\"department\":\"GOoD analysis department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}";

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("request", validRequest))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("validateRequest", hasProperty("errorMessages", hasSize(0))))
                .andExpect(model().attribute("validateRequest", hasProperty("request", not(empty()))))
                .andExpect(model().attribute("validateRequest", hasProperty("status", is(ValidationRequestStatus.VALID)))
                );

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(1));
        assertThat(validationRequests.get(0).getStatus(), is(ValidationRequestStatus.VALID));
    }

    @Test
    void whenSubmittingInvalidOrder_validationShouldNotSucceed() throws Exception {
        var invalidRequest = "{\"type\":\"ANALYSIS\",\"department\":\"FAKE department\",\"start_date\":\"2020-08-13\"," +
                "\"end_date\":\"2020-08-15\",\"currency\":\"USD\",\"cost\":123.12,\"parts\":[{\"inventory_number\":\"InventoryNumber1\"," +
                "\"name\":\"PartNumber1\",\"count\":1},{\"inventory_number\":\"InventoryNumber2\",\"name\":\"PartNumber2\",\"count\":2}]}";

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("request", invalidRequest))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("validateRequest", hasProperty("errorMessages", hasSize(1))))
                .andExpect(model().attribute("validateRequest", hasProperty("request", not(empty()))))
                .andExpect(model().attribute("validateRequest", hasProperty("status", is(ValidationRequestStatus.NOT_VALID)))
                );

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(1));
        assertThat(validationRequests.get(0).getStatus(), is(ValidationRequestStatus.NOT_VALID));
    }

    @Test
    void whenSubmittingInvalidJson_errorShouldBeReturned() throws Exception {
        var invalidRequest = "{\"type\":\"ANALYSIS\", incorrect JSON syntax here}";

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("request", invalidRequest))
                .andExpect(status().is5xxServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", is("Order is not valid")));

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(0));
    }

    @Test
    void getHistory() throws Exception {
        var request = new ValidationRequest();
        request.setRequest("{}");
        request.setDepartment("My department");
        request.setStatus(ValidationRequestStatus.VALID);
        request.setDate(LocalDate.now());
        request.setType(OrderType.ANALYSIS);
        repository.save(request);

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history"))
                .andExpect(model().attribute("history", hasItem(hasProperty("request", is(request.getRequest())))))
                .andExpect(model().attribute("history", hasItem(hasProperty("status", is(request.getStatus())))));
    }

    @Test
    void whenCallingUrlThatDoesNotExist_returnError() throws Exception {
        mockMvc.perform(get("/not-existing-url"))
                .andExpect(status().is4xxClientError());

        var validationRequests = repository.findAll();
        assertThat(validationRequests.size(), is(0));
    }

}
