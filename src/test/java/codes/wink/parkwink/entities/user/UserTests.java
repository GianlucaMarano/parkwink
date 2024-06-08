package codes.wink.parkwink.entities.user;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user = new User();
        user.setId(1L);
        user.setName("Gianluca");
        user.setSurname("Marano");
        user.setEmail("gianluca@wink.codes");
        user.setPassword("test");
    }

    @Test
    void getAuthorities_shouldReturnAdminAuthority() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ADMIN")));
    }

    @Test
    void setPassword_shouldEncryptPassword() {
        String rawPassword = "newpassword";
        user.setPassword(rawPassword);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(rawPassword, user.getPassword()));
    }

    @Test
    void getUsername_shouldReturnEmail() {
        assertEquals("gianluca@wink.codes", user.getUsername());
    }

    @Test
    void isAccountNonExpired_shouldReturnTrue() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked_shouldReturnTrue() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired_shouldReturnTrue() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabled_shouldReturnTrue() {
        assertTrue(user.isEnabled());
    }

    private void validateAndThrowIfInvalid(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Test
    void validateEmail_withValidEmail_shouldPassValidation() {
        user.setEmail("gianluca@wink.codes");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validateEmail_withInvalidEmail_shouldThrowException() {
        user.setEmail("invalid-email");
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> validateAndThrowIfInvalid(user)
        );
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void validateEmail_withBlankEmail_shouldThrowException() {
        user.setEmail("");
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> validateAndThrowIfInvalid(user)
        );
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size());
        String message = violations.iterator().next().getMessage();
        assertTrue(message.equals("Email is mandatory"));
    }
}
