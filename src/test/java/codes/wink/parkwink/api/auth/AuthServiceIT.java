package codes.wink.parkwink.api.auth;

import codes.wink.parkwink.config.customExceptions.UserAlreadyExistsException;
import codes.wink.parkwink.entities.user.Builder;
import codes.wink.parkwink.entities.user.User;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.entities.user.UserRepository;
import codes.wink.parkwink.utils.jwt.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthServiceIT {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Builder userBuilder;


    private UserDTO validUserDTO;

    @BeforeEach
    void setUp() {
        validUserDTO = new UserDTO("Gianluca", "Marano", "gianluca@wink.codes", "testpassword");
    }

    @Test
    @Transactional
    void givenValidUserDTO_whenRegisterUser_thenReturnsAuthenticationResponse() throws UserAlreadyExistsException {
        AuthenticationResponse response = authService.registerUser(validUserDTO);

        assertNotNull(response);
        assertEquals(validUserDTO.getEmail(), response.getUser());
        assertNotNull(response.getToken());

        Optional<User> savedUser = userRepository.findByEmail(validUserDTO.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals(validUserDTO.getEmail(), savedUser.get().getEmail());
    }

    @Test
    @Transactional
    void givenExistingUserEmail_whenRegisterUser_thenThrowsUserAlreadyExistsException() {
        userRepository.save(userBuilder.build(validUserDTO));

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.registerUser(validUserDTO)
        );

        assertEquals("This email address is already used", exception.getMessage());
    }

    @Test
    @Transactional
    void givenValidUserDTO_whenAuthenticateUser_thenReturnsAuthenticationResponse() {
        userRepository.save(userBuilder.build(validUserDTO));

        AuthenticationResponse response = authService.authenticateUser(validUserDTO);

        assertNotNull(response);
        assertEquals(validUserDTO.getEmail(), response.getUser());
        assertNotNull(response.getToken());
    }

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }
}
