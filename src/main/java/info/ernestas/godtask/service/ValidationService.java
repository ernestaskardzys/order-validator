package info.ernestas.godtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.entity.ValidationRequest;
import info.ernestas.godtask.model.ValidationRequestStatus;
import info.ernestas.godtask.model.orders.WorkOrder;
import info.ernestas.godtask.model.exception.CanNotProcessRequestException;
import info.ernestas.godtask.repository.ValidationRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger("ValidationService");

    private final ValidationRequestRepository validationRequestRepository;
    private final ObjectMapper objectMapper;

    public ValidationService(ValidationRequestRepository validationRequestRepository, ObjectMapper objectMapper) {
        this.validationRequestRepository = validationRequestRepository;
        this.objectMapper = objectMapper;
    }

    public void processValidationRequest(WorkOrder workOrder, ValidationRequestStatus validationRequestStatus) {
        try {
            var validationRequest = new ValidationRequest();
            validationRequest.setDate(LocalDate.now());
            validationRequest.setType(workOrder.getType());
            validationRequest.setDepartment(workOrder.getDepartment());
            validationRequest.setStatus(validationRequestStatus);
            validationRequest.setRequest(objectMapper.writeValueAsString(workOrder));

            validationRequestRepository.save(validationRequest);
        } catch (Exception e) {
            LOGGER.error("Can not save request to the database!", e);
            throw new CanNotProcessRequestException("Request saving to database failed");
        }
    }

    public List<ValidationRequest> getHistory() {
        return validationRequestRepository.findAllByOrderByDate();
    }
}
