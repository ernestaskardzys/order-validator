package info.ernestas.godtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.entity.ValidationRequest;
import info.ernestas.godtask.model.ValidationRequestStatus;
import info.ernestas.godtask.model.exception.InvalidOrderException;
import info.ernestas.godtask.model.orders.WorkOrder;
import info.ernestas.godtask.model.response.ValidationResponse;
import info.ernestas.godtask.service.StringToWorkOrderPropertyConverter;
import info.ernestas.godtask.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;
import java.util.stream.Collectors;

public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger("BaseController");

    private final ValidationService validationService;
    private final ObjectMapper objectMapper;

    public BaseController(ValidationService validationService, ObjectMapper objectMapper) {
        this.validationService = validationService;
        this.objectMapper = objectMapper;
    }

    protected ValidationResponse processRequest(WorkOrder workOrder, BindingResult bindingResult) {
        if (workOrder == null) {
            throw new InvalidOrderException("Order is not valid");
        }

        var validationRequestStatus = getValidationRequestStatus(bindingResult);
        validationService.processValidationRequest(workOrder, validationRequestStatus);

        if (validationRequestStatus.equals(ValidationRequestStatus.NOT_VALID)) {
            return new ValidationResponse(convertRequestToJsonString(workOrder), validationRequestStatus, getListOfErrors(bindingResult));
        }

        return new ValidationResponse(convertRequestToJsonString(workOrder), validationRequestStatus);
    }

    protected List<ValidationRequest> getHistoryResponse() {
        return validationService.getHistory();
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(WorkOrder.class, new StringToWorkOrderPropertyConverter(objectMapper));
    }

    private String convertRequestToJsonString(WorkOrder workOrder) {
        try {
            return objectMapper.writeValueAsString(workOrder);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can not convert request to JSON", e);
            return null;
        }
    }

    private ValidationRequestStatus getValidationRequestStatus(BindingResult bindingResult) {
        return !bindingResult.hasErrors() ? ValidationRequestStatus.VALID : ValidationRequestStatus.NOT_VALID;
    }

    private List<String> getListOfErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        String fieldErrors = ((FieldError) error).getField();
                        return fieldErrors + ": " + error.getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());
    }

}
