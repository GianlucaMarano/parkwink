package codes.wink.parkwink.entities.lot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a parking lot in the system.
 * This class is mapped to the "Lots" table in the database.
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
@Table(name = "Lots")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lot {

    /**
     * The unique identifier for the parking lot.
     * Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Indicates whether the parking lot is busy.
     * This field is mandatory.
     */
    @Column(name = "busy", nullable = false)
    private Boolean busy;
}
