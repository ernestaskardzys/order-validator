package info.ernestas.godtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.model.resource.ValidateRequestResource;
import info.ernestas.godtask.service.ValidationService;
import info.ernestas.godtask.service.mapper.ValidationHistoryResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Controller
public class AdministratorController extends BaseController {

    private final ValidationHistoryResponseMapper mapper;

    public AdministratorController(ValidationService validationService, ObjectMapper objectMapper, ValidationHistoryResponseMapper mapper) {
        super(validationService, objectMapper);
        this.mapper = mapper;
    }

    @GetMapping("/validate")
    public String validate(Model model) {
        model.addAttribute("validateRequest", new ValidateRequestResource());
        return "validate";
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitValidation(@Valid ValidateRequestResource validateRequest, BindingResult bindingResult, Model model) {
        var response = processRequest(validateRequest.getRequest(), bindingResult);

        model.addAttribute("validateRequest", response);

        return "result";
    }

    @GetMapping("/history")
    public String getHistory(Model model) {
        var historyResponses = mapper.map(getHistoryResponse());

        model.addAttribute("history", historyResponses);
        return "history";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

}
