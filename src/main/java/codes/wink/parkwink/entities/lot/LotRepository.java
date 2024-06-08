package codes.wink.parkwink.entities.lot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the Lot entity.
 * Extends JpaRepository to provide JPA-based data access functionality.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Repository - Indicates that this interface is a Spring Data Repository.</li>
 * </ul>
 * </p>
 */
@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    /**
     * Finds one available (free) parking lot.
     *
     * @return an Optional containing a free Lot if available, or empty if no free lots are found.
     */
    @Query(value = "SELECT * FROM LOTS WHERE BUSY=FALSE LIMIT 1", nativeQuery = true)
    Optional<Lot> findOneFree();
}
