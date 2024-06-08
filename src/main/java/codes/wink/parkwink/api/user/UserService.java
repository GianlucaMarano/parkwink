package codes.wink.parkwink.api.user;

import codes.wink.parkwink.entities.user.Builder;
import codes.wink.parkwink.entities.user.User;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.entities.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service class for handling user operations.
 * Provides methods for creating, updating, deleting, and retrieving user information.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Service - Indicates that this class is a Spring service component.</li>
 * </ul>
 * </p>
 */
@Service("UserService")
public class UserService {

    /**
     * Repository for accessing user data.
     */
    private final UserRepository repo;

    /**
     * Builder for converting between User and UserDTO objects.
     */
    private final Builder builder;

    /**
     * Constructs a UserService with the specified UserRepository and Builder.
     *
     * @param repo    the UserRepository for accessing user data.
     * @param builder the Builder for converting between User and UserDTO objects.
     */
    public UserService(UserRepository repo, Builder builder) {
        this.repo = repo;
        this.builder = builder;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    public List<User> findAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the requested user.
     * @throws NoSuchElementException if no user is found with the specified ID.
     */
    public User findOne(Long id) {
        Optional<User> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No User was found at the ID you requested");
        }
        return found.get();
    }

    /**
     * Creates a new user.
     *
     * @param dto the UserDTO containing the details of the user to create.
     * @return the created user.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    public User create(UserDTO dto) throws IllegalArgumentException, NullPointerException {
        return Stream.of(dto)
                .map(builder::build)
                .map(repo::save)
                .findFirst()
                .get();
    }

    /**
     * Updates an existing user.
     *
     * @param id         the ID of the user to update.
     * @param newDetails the UserDTO containing the new details of the user.
     * @return the updated user.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     * @throws NoSuchElementException   if no user is found with the specified ID.
     */
    @Transactional
    public User update(Long id, UserDTO newDetails) throws IllegalArgumentException, NullPointerException {
        Optional<User> found = repo.findById(id)
                .map(model -> builder.build(newDetails, model))
                .map(repo::save)
                .map(builder::build)
                .orElseThrow(() -> new NoSuchElementException(String.format("No User was found at ID '%s'", id)))
                .map(builder::build);
        found.get().setId(id);
        return found.get();
    }

    /**
     * Deletes a specific user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return a confirmation message.
     * @throws NoSuchElementException if no user is found with the specified ID.
     */
    public String delete(Long id) {
        Optional<User> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No User was found at the ID you requested");
        }
        repo.deleteById(id);
        return "User deleted successfully";
    }
}
