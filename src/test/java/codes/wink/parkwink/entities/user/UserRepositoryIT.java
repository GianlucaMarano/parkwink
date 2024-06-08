package codes.wink.parkwink.entities.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Gianluca");
        user.setSurname("Marano");
        user.setEmail("gianluca@wink.codes");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        userRepository.save(user);
    }

    @Test
    void givenExistingEmail_whenFindByEmail_thenReturnUser() {
        Optional<User> foundUser = userRepository.findByEmail("gianluca@wink.codes");
        assertTrue(foundUser.isPresent());
        assertEquals("Gianluca", foundUser.get().getName());
    }

    @Test
    void givenExistingEmail_whenExistsByEmail_thenReturnTrue() {
        Boolean exists = userRepository.existsByEmail("gianluca@wink.codes");
        assertTrue(exists);
    }

    @Test
    void givenNonExistingEmail_whenFindByEmail_thenReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@wink.codes");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void givenNonExistingEmail_whenExistsByEmail_thenReturnFalse() {
        Boolean exists = userRepository.existsByEmail("nonexistent@wink.codes");
        assertFalse(exists);
    }
}
