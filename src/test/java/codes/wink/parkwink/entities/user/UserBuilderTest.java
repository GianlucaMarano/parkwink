package codes.wink.parkwink.entities.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserBuilderTest {

    private Builder userBuilder;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        userBuilder = new Builder(modelMapper);
    }

    @Test
    public void buildUserFromDTO_withValidUserDTO_returnsCorrectUser() {
        UserDTO userDTO = new UserDTO("Gianluca", "Marano", "gianluca@wink.codes", "test");

        User user = userBuilder.build(userDTO);

        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertTrue(new BCryptPasswordEncoder().matches("test", user.getPassword()));
    }

    @Test
    public void buildDTOFromUser_withValidUser_returnsCorrectUserDTO() {
        User user = new User();
        user.setName("Gianluca");
        user.setSurname("Marano");
        user.setEmail("gianluca@wink.codes");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));

        Optional<UserDTO> userDTOOptional = userBuilder.build(user);

        assertTrue(userDTOOptional.isPresent());
        UserDTO userDTO = userDTOOptional.get();

        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getSurname(), userDTO.getSurname());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void buildUserFromDTO_withExistingUser_updatesUserFields() {
        User user = new User();
        user.setName("Old Name");
        user.setSurname("Old Surname");
        user.setEmail("oldemail@wink.codes");
        user.setPassword(new BCryptPasswordEncoder().encode("oldpassword"));

        UserDTO userDTO = new UserDTO("Gianluca", "Marano", "gianluca@wink.codes", "test");

        User updatedUser = userBuilder.build(userDTO, user);

        assertEquals(userDTO.getName(), updatedUser.getName());
        assertEquals(userDTO.getSurname(), updatedUser.getSurname());
        assertEquals(userDTO.getEmail(), updatedUser.getEmail());
        assertTrue(new BCryptPasswordEncoder().matches("test", updatedUser.getPassword()));
    }
}
