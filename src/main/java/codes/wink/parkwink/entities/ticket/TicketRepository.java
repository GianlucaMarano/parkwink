package codes.wink.parkwink.entities.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the Ticket entity.
 * Extends JpaRepository to provide JPA-based data access functionality.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Repository - Indicates that this interface is a Spring Data Repository.</li>
 * </ul>
 * </p>
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Additional custom queries can be defined here if needed in the future.
}
