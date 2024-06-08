package codes.wink.parkwink.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the User entity.
 * Extends JpaRepository to provide JPA-based data access functionality.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Repository - Indicates that this interface is a Spring Data Repository.</li>
 * </ul>
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address to search for.
     * @return an Optional containing the User if found, or empty if not found.
     */
    @Query(value = "SELECT * FROM USERS WHERE EMAIL = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email the email address to check for existence.
     * @return true if a user exists with the given email, false otherwise.
     */
    Boolean existsByEmail(String email);
}
