package codes.wink.parkwink.utils.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Body<T> implements Serializable {

    String timestamp;
    int status;
    String message;
    T body;

    public Body(T body) {
        this(body, HttpStatus.OK);
    }

    public Body(T body, HttpStatus status) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.body = body;
        this.status = status.value();
        this.message = status.getReasonPhrase();
    }
}
