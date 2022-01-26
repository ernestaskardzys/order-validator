package info.ernestas.godtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.model.orders.WorkOrder;
import info.ernestas.godtask.model.response.ValidationResponse;
import info.ernestas.godtask.service.ValidationService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ValidationController extends BaseController {

    public ValidationController(ValidationService validationService, ObjectMapper objectMapper) {
        super(validationService, objectMapper);
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResponse validateOrder(@Valid @RequestBody WorkOrder workOrder, BindingResult bindingResult) {
        return processRequest(workOrder, bindingResult);
    }

}
