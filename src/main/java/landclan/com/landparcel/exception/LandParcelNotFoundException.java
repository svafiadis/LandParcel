package landclan.com.landparcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LandParcelNotFoundException extends RuntimeException {
    public LandParcelNotFoundException(String message) {
        super(message);
    }
}
