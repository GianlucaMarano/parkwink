package codes.wink.parkwink.entities.lot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transferring parking lot information.
 * This class is used to encapsulate lot details in a structured format.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Data - Generates getters, setters, toString, hashCode, and equals methods.</li>
 * <li>@AllArgsConstructor - Generates a constructor with one argument for each field.</li>
 * <li>@NoArgsConstructor - Generates a no-argument constructor.</li>
 * <li>@JsonProperty - Used to map JSON property names to Java field names.</li>
 * </ul>
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTO {

    /**
     * Indicates whether the parking lot is busy.
     */
    @JsonProperty("busy")
    private Boolean busy;

}
