package codes.wink.parkwink.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//Jackson requires that the first Non-Serializable entity in a chain of sub-classes must have a constructor with no arguments.
//This is the bridge between Response and ResponseEntity. If Response extends ResponseEntity directly, serialization to JSON is not possible.
public class DeserializeHelper<T> extends ResponseEntity<T> {

    public DeserializeHelper() {
        super(HttpStatus.OK);
    }

    public DeserializeHelper(HttpStatus status) {
        super(status);
    }
}
