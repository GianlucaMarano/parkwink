package codes.wink.parkwink.entities.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity representing a ticket in the system.
 * This class is mapped to the "tickets" table in the database.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Entity - Specifies that the class is an entity and is mapped to a database table.</li>
 * <li>@Table - Specifies the table name in the database that this entity maps to.</li>
 * <li>@Data - Generates getters, setters, toString, hashCode, and equals methods.</li>
 * <li>@AllArgsConstructor - Generates a constructor with one argument for each field.</li>
 * <li>@NoArgsConstructor - Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 */
@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    /**
     * The unique identifier for the ticket.
     * Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The start date and time of the ticket.
     * Defaults to the current date and time.
     */
    @Column(name = "start")
    private Date start = new Date();

    /**
     * The end date and time of the ticket.
     */
    @Column(name = "finish")
    private Date finish;

    /**
     * The price of the ticket.
     */
    @Column(name = "price")
    private Double price;

    /**
     * The date and time when the ticket was paid.
     */
    @Column(name = "paid")
    private Date paid;

    /**
     * The identifier of the lot associated with the ticket.
     * Ignored in JSON serialization to avoid exposing internal database IDs.
     */
    @JsonIgnore
    @Column(name = "id_lot")
    private Long lot;
}
