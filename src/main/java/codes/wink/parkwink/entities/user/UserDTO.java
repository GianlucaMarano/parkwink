package codes.wink.parkwink.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transferring user information.
 * This class is used to encapsulate user details such as name, surname,
 * email, and password in a structured format.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Data - Generates getters, setters, toString, hashCode, and equals methods.</li>
 * <li>@NoArgsConstructor - Generates a no-argument constructor.</li>
 * <li>@AllArgsConstructor - Generates a constructor with one argument for each field.</li>
 * <li>@JsonProperty - Used to map JSON property names to Java field names.</li>
 * <li>@NotBlank - Ensures the annotated field is not null or empty.</li>
 * <li>@Email - Ensures the annotated field is a valid email address.</li>
 * </ul>
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * The user's first name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The user's last name.
     */
    @JsonProperty("surname")
    private String surname;

    /**
     * The user's email address. This field is mandatory and must be a valid email format.
     */
    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    /**
     * The user's password. This field is mandatory.
     */
    @NotBlank
    @JsonProperty("password")
    private String password;
}
