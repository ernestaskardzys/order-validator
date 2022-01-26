package info.ernestas.godtask.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CanNotProcessRequestException extends RuntimeException {

    public CanNotProcessRequestException(String message) {
        super(message);
    }

}
