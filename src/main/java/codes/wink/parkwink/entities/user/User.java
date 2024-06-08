package codes.wink.parkwink.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the system.
 * Implements {@link UserDetails} for Spring Security integration.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The user's first name.
     */
    @Column(name = "name")
    private String name;

    /**
     * The user's last name.
     */
    @Column(name = "surname")
    private String surname;

    /**
     * The user's email address. This field is mandatory and must be unique.
     */
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The user's password. This field is mandatory and is ignored in JSON serialization.
     */
    @Column(name = "password", nullable = false)
    @NotBlank
    @JsonIgnore
    private String password;

    /**
     * Returns the authorities granted to the user. By default, all users have the "ADMIN" authority.
     *
     * @return a collection of granted authorities.
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Sets the user's password after encoding it with BCrypt.
     *
     * @param pass the raw password to be encoded and set.
     */
    public void setPassword(String pass) {
        this.password = new BCryptPasswordEncoder().encode(pass);
    }

    /**
     * Returns the username used to authenticate the user. In this case, it is the user's email.
     *
     * @return the username (email) of the user.
     */
    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the account is non-expired, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true if the account is non-locked, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true if the credentials are non-expired, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
