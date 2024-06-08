package codes.wink.parkwink.utils.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> extends DeserializeHelper<Body<T>> implements Serializable {

    private final Body<T> body;

    public Response(T payload) {
        super(HttpStatus.OK);
        this.body = new Body<T>(payload);
    }

    public Response(T payload, HttpStatus status) {
        super(status);
        this.body = new Body<T>(payload, status);
    }

}
