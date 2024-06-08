package codes.wink.parkwink.entities.ticket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Builder component for converting between Ticket and TicketDTO objects.
 * Utilizes ModelMapper for object mapping.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * <li>@AllArgsConstructor - Generates a constructor with one argument for each field.</li>
 * <li>@NoArgsConstructor - Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 */
@Component("TicketBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Builder {

    /**
     * ModelMapper instance for mapping between DTO and entity objects.
     */
    @Autowired
    private ModelMapper mapper;

    /**
     * Converts a TicketDTO object to a Ticket entity.
     *
     * @param dto the TicketDTO object to convert.
     * @return the resulting Ticket entity.
     */
    public Ticket build(TicketDTO dto) {
        return mapper.map(dto, Ticket.class);
    }

    /**
     * Converts a Ticket entity to a TicketDTO object.
     *
     * @param domain the Ticket entity to convert.
     * @return an Optional containing the resulting TicketDTO object.
     */
    public Optional<TicketDTO> build(Ticket domain) {
        TicketDTO dto = mapper.map(domain, TicketDTO.class);
        return Optional.of(dto);
    }

    /**
     * Updates an existing Ticket entity with values from a TicketDTO object.
     *
     * @param dto    the TicketDTO object containing updated values.
     * @param domain the Ticket entity to update.
     * @return the updated Ticket entity.
     */
    public Ticket build(TicketDTO dto, Ticket domain) {
        mapper.map(dto, domain);
        return domain;
    }

    /**
     * Updates an existing Ticket entity with values from another Ticket entity.
     *
     * @param newTicket the Ticket entity containing updated values.
     * @param domain    the Ticket entity to update.
     * @return the updated Ticket entity.
     */
    public Ticket build(Ticket newTicket, Ticket domain) {
        mapper.map(newTicket, domain);
        return domain;
    }
}
