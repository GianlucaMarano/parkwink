package codes.wink.parkwink.api.user;

import codes.wink.parkwink.entities.user.Builder;
import codes.wink.parkwink.entities.user.User;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.entities.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({UserService.class, Builder.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Builder builder;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        // Setup initial data
        userDTO = new UserDTO();
        userDTO.setEmail("gianluca@wink.codes");
        userDTO.setName("Gianluca");
        userDTO.setSurname("Marano");
        userDTO.setPassword("password");
    }

    @Test
    @Transactional
    void givenValidUserDTO_whenCreateUser_thenUserIsCreatedSuccessfully() {
        User user = userService.create(userDTO);
        assertNotNull(user.getId());
    }

    @Test
    @Transactional
    void givenExistingUserId_whenFindUserById_thenUserIsReturnedSuccessfully() {
        User savedUser = userRepository.save(builder.build(userDTO));
        User foundUser = userService.findOne(savedUser.getId());
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    @Transactional
    void givenMultipleUsers_whenFindAllUsers_thenAllUsersAreReturnedSuccessfully() {
        User savedUser1 = userRepository.save(builder.build(userDTO));
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setEmail("matteo@wink.codes");
        userDTO2.setName("Matteo");
        userDTO2.setSurname("S");
        userDTO2.setPassword("password");
        User savedUser2 = userRepository.save(builder.build(userDTO2));
        List<User> users = userService.findAll();
        assertTrue(users.contains(savedUser1));
        assertTrue(users.contains(savedUser2));
    }

    @Test
    @Transactional
    void givenExistingUserId_whenDeleteUserById_thenUserIsDeletedSuccessfully() {
        User savedUser = userRepository.save(builder.build(userDTO));
        String result = userService.delete(savedUser.getId());
        assertEquals("User deleted successfully", result);
        assertTrue(userRepository.findById(savedUser.getId()).isEmpty());
    }

    @Test
    @Transactional
    void givenValidUserDetails_whenUpdateUser_thenUserIsUpdatedSuccessfully() {
        User savedUser = userRepository.save(builder.build(userDTO));
        UserDTO newDetails = new UserDTO();
        newDetails.setEmail("gianluca1@wink.codes");
        newDetails.setName("Gianluca1");
        newDetails.setSurname("Marano1");
        newDetails.setPassword("newpassword");

        User updatedUser = userService.update(savedUser.getId(), newDetails);
        assertEquals("gianluca1@wink.codes", updatedUser.getEmail());
        assertEquals("Gianluca1", updatedUser.getName());
        assertEquals("Marano1", updatedUser.getSurname());
    }
}
