package codes.wink.parkwink.entities.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Component for building and converting between {@link User} and {@link UserDTO} objects using {@link ModelMapper}.
 */
@Component("UserBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Builder {

    /**
     * The {@link ModelMapper} instance used for mapping between {@link User} and {@link UserDTO}.
     */
    @Autowired
    ModelMapper mapper;

    /**
     * Converts a {@link UserDTO} to a {@link User}.
     *
     * @param dto the {@link UserDTO} to be converted.
     * @return the corresponding {@link User} entity.
     */
    public User build(UserDTO dto) {
        User model = mapper.map(dto, User.class);
        return model;
    }

    /**
     * Converts a {@link User} to a {@link UserDTO}.
     *
     * @param domain the {@link User} to be converted.
     * @return an {@link Optional} containing the corresponding {@link UserDTO}.
     */
    public Optional<UserDTO> build(User domain) {
        UserDTO dto = mapper.map(domain, UserDTO.class);
        return Optional.of(dto);
    }

    /**
     * Updates an existing {@link User} entity with the values from a {@link UserDTO}.
     *
     * @param dto    the {@link UserDTO} containing the new values.
     * @param domain the existing {@link User} entity to be updated.
     * @return the updated {@link User} entity.
     */
    public User build(UserDTO dto, User domain) {
        mapper.map(dto, domain);
        return domain;
    }
}
