package codes.wink.parkwink.api.auth;

import codes.wink.parkwink.config.customExceptions.UserAlreadyExistsException;
import codes.wink.parkwink.config.security.JwtService;
import codes.wink.parkwink.entities.user.Builder;
import codes.wink.parkwink.entities.user.User;
import codes.wink.parkwink.entities.user.UserDTO;
import codes.wink.parkwink.entities.user.UserRepository;
import codes.wink.parkwink.utils.jwt.AuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Service class for handling authentication operations.
 * Provides methods for user registration and authentication.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Service - Indicates that this class is a Spring service component.</li>
 * </ul>
 * </p>
 */
@Service("AuthService")
public class AuthService {

    /**
     * Repository for accessing user data.
     */
    private final UserRepository repo;

    /**
     * Builder for converting between User and UserDTO objects.
     */
    private final Builder builder;

    /**
     * Service for managing JSON Web Tokens (JWTs).
     */
    private final JwtService jwtService;

    /**
     * Manager for handling authentication operations.
     */
    private final AuthenticationManager manager;

    /**
     * Constructs an AuthService with the specified dependencies.
     *
     * @param repo       the UserRepository for accessing user data.
     * @param builder    the Builder for converting between User and UserDTO objects.
     * @param jwtService the JwtService for managing JWTs.
     * @param manager    the AuthenticationManager for handling authentication operations.
     */
    public AuthService(
            UserRepository repo,
            Builder builder,
            JwtService jwtService,
            AuthenticationManager manager
    ) {
        this.repo = repo;
        this.builder = builder;
        this.jwtService = jwtService;
        this.manager = manager;
    }

    /**
     * Registers a new user.
     *
     * @param dto the UserDTO containing user registration details.
     * @return an AuthenticationResponse containing the user's email and JWT token.
     * @throws UserAlreadyExistsException if the user already exists in the system.
     */
    public AuthenticationResponse registerUser(@Valid UserDTO dto) throws UserAlreadyExistsException {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("This email address is already used");
        }

        User req = Stream.of(dto)
                .map(builder::build)
                .findFirst()
                .get();

        repo.save(req);

        String token = jwtService.generate(req.getEmail());
        return new AuthenticationResponse(req.getEmail(), token);
    }

    /**
     * Authenticates an existing user.
     *
     * @param dto the UserDTO containing user authentication details.
     * @return an AuthenticationResponse containing the user's email and JWT token.
     */
    public AuthenticationResponse authenticateUser(UserDTO dto) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()));
        String token = jwtService.generate(dto.getEmail());
        return new AuthenticationResponse(dto.getEmail(), token);
    }
}
