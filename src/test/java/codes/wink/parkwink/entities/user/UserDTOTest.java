package codes.wink.parkwink.entities.user;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private void validateAndThrowIfInvalid(UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Test
    void givenValidUserDTO_whenValidated_thenNoViolations() {
        UserDTO userDTO = new UserDTO("Gianluca", "Marano", "gianluca@wink.codes", "test");
        assertDoesNotThrow(() -> validateAndThrowIfInvalid(userDTO));
    }

    @Test
    void givenInvalidEmail_whenValidated_thenConstraintViolationException() {
        UserDTO userDTO = new UserDTO("Gianluca", "Marano", "invalid-email", "test");
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> validateAndThrowIfInvalid(userDTO)
        );
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size());
        String message = violations.iterator().next().getMessage();
        assertEquals("deve essere un indirizzo email nel formato corretto", message);
    }

    @Test
    void givenBlankEmail_whenValidated_thenConstraintViolationException() {
        UserDTO userDTO = new UserDTO("Gianluca", "Marano", "", "test");
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> validateAndThrowIfInvalid(userDTO)
        );
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size()); // Only one violation is expected
        String message = violations.iterator().next().getMessage();
        assertEquals("non deve essere spazio", message);
    }


}
