package info.ernestas.godtask.service.mapper;

import info.ernestas.godtask.entity.ValidationRequest;
import info.ernestas.godtask.model.response.ValidationHistoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValidationHistoryResponseMapper {

    ValidationHistoryResponse map(ValidationRequest request);

    List<ValidationHistoryResponse> map(List<ValidationRequest> request);

}
