package info.ernestas.godtask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.godtask.model.exception.CanNotProcessRequestException;
import info.ernestas.godtask.model.orders.WorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class StringToWorkOrderPropertyConverter extends PropertyEditorSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger("StringToWorkOrder");

    private final ObjectMapper objectMapper;

    public StringToWorkOrderPropertyConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            var workOrder = objectMapper.readValue(text, WorkOrder.class);
            setValue(workOrder);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can not convert data to the order!", e);
            throw new CanNotProcessRequestException("Can not convert data to the order!");
        }
    }
}
