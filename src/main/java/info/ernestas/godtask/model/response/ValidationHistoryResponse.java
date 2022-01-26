package info.ernestas.godtask.model.response;

import info.ernestas.godtask.model.ValidationRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationHistoryResponse {

    private String request;

    private ValidationRequestStatus status;

}
