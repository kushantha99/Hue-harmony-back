package hueHarmony.web.core.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        super("Validation failed");
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    @Override
    public String getMessage() {
        return "Validation failed with " + bindingResult.getErrorCount() + " errors";
    }
}
