package codes.wink.parkwink.entities.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TicketDTO is a Data Transfer Object (DTO) that represents a parking ticket.
 * It includes details about the start and finish time of the parking,
 * the price of the ticket, the payment time, and the parking lot number.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Data - Generates getters, setters, toString, hashCode, and equals methods.</li>
 * <li>@NoArgsConstructor - Generates a no-argument constructor.</li>
 * <li>@AllArgsConstructor - Generates a constructor with one argument for each field.</li>
 * <li>@JsonProperty - Used to map JSON property names to Java field names.</li>
 * </ul>
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    /**
     * The start time of the parking.
     */
    @JsonProperty("start")
    private Date start;

    /**
     * The finish time of the parking.
     */
    @JsonProperty("finish")
    private Date finish;

    /**
     * The price of the parking ticket.
     */
    @JsonProperty("price")
    private Double price;

    /**
     * The time when the ticket was paid.
     */
    @JsonProperty("paid")
    private Date paid;

    /**
     * The parking lot number.
     */
    @JsonProperty("lot")
    private Long lot;
}
