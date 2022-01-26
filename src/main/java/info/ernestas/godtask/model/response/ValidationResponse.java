package info.ernestas.godtask.model.response;

import info.ernestas.godtask.model.ValidationRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {

    private String request;

    private ValidationRequestStatus status;

    private List<String> errorMessages = new ArrayList<>();

    public ValidationResponse(String request, ValidationRequestStatus status) {
        this.request = request;
        this.status = status;
    }
}
