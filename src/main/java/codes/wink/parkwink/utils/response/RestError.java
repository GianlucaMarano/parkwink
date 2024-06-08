package codes.wink.parkwink.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class RestError implements Serializable {

    String exception;
    String detail;
    @JsonIgnore
    HttpStatus status;

    public RestError(Exception e) {
        this.status = HttpStatus.BAD_REQUEST;
        this.exception = e.getClass().getSimpleName();
        this.detail = e.getMessage();

        switch (exception) {
            case "NoSuchElementException":
                status = HttpStatus.NOT_FOUND;
                break;
            case "ConstraintViolationException":
                detail = "One or more mandatory fields are empty";
                break;
            case "MethodArgumentNotValidException":
                detail = "One or more fields are not valid";
                break;
            case "AccessDeniedException":
                status = HttpStatus.UNAUTHORIZED;
                break;
            case "ExpiredJwtException":
                status = HttpStatus.FORBIDDEN;
                break;
            default:
        }
    }

}
