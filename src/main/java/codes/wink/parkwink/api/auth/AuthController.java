package codes.wink.parkwink.api.auth;

import codes.wink.parkwink.config.customExceptions.UserAlreadyExistsException;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.utils.jwt.AuthenticationResponse;
import codes.wink.parkwink.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication-related requests.
 * Provides endpoints for user registration and authentication.
 *
 * <p>Annotations:
 * <ul>
 * <li>@RestController - Indicates that this class is a REST controller.</li>
 * <li>@CrossOrigin - Allows cross-origin requests.</li>
 * <li>@RequestMapping - Specifies the base URL for all endpoints in this controller.</li>
 * </ul>
 * </p>
 */
@RestController("AuthController")
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * Service for handling authentication operations.
     */
    private final AuthService authService;

    /**
     * Constructs an AuthController with the specified AuthService.
     *
     * @param service the AuthService to be used for authentication operations.
     */
    @Autowired
    public AuthController(AuthService service) {
        authService = service;
    }

    /**
     * Registers a new user.
     *
     * @param dto the UserDTO containing user registration details.
     * @return a Response object containing the AuthenticationResponse and HTTP status.
     * @throws UserAlreadyExistsException if the user already exists in the system.
     */
    @PostMapping("/register/")
    public Response<AuthenticationResponse> registration(@Valid @RequestBody UserDTO dto)
            throws UserAlreadyExistsException {
        AuthenticationResponse response = authService.registerUser(dto);
        return new Response<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates an existing user.
     *
     * @param dto the UserDTO containing user authentication details.
     * @return a Response object containing the AuthenticationResponse and HTTP status.
     */
    @PostMapping("/authenticate/")
    public Response<AuthenticationResponse> authenticate(@RequestBody UserDTO dto) {
        AuthenticationResponse response = authService.authenticateUser(dto);
        return new Response<>(response, HttpStatus.OK);
    }
}
