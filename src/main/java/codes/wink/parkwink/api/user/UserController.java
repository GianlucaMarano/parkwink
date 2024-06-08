package codes.wink.parkwink.api.user;

import codes.wink.parkwink.entities.user.User;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 * Provides endpoints for creating, updating, deleting, and retrieving user information.
 *
 * <p>Annotations:
 * <ul>
 * <li>@RestController - Indicates that this class is a REST controller.</li>
 * <li>@RequestMapping - Specifies the base URL for all endpoints in this controller.</li>
 * </ul>
 * </p>
 */
@RestController("UserController")
@RequestMapping("/api/v1/user")
public class UserController {

    /**
     * Service for handling user operations.
     */
    private final UserService service;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param service the UserService to be used for user operations.
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Retrieves all users.
     *
     * @return a Response object containing a list of all users.
     */
    @GetMapping("/")
    public Response<List<User>> all() {
        return new Response<>(service.findAll());
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a Response object containing the requested user.
     */
    @GetMapping("/{id}")
    public Response<User> findOne(@PathVariable("id") String id) {
        return new Response<>(service.findOne(Long.parseLong(id)));
    }

    /**
     * Creates a new user.
     *
     * @param dto the UserDTO containing the details of the user to create.
     * @return a Response object containing the created user and HTTP status.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public Response<User> create(@Valid @RequestBody UserDTO dto)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.create(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     *
     * @param id         the ID of the user to update.
     * @param newDetails the UserDTO containing the new details of the user.
     * @return a Response object containing the updated user.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public Response<User> update(@PathVariable("id") String id, @Valid @RequestBody UserDTO newDetails)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.update(Long.parseLong(id), newDetails));
    }

    /**
     * Deletes a specific user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return a Response object containing a confirmation message.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") String id) {
        return new Response<>(service.delete(Long.parseLong(id)));
    }

}
