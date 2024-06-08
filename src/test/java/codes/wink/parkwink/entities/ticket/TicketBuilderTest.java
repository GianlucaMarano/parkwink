package codes.wink.parkwink.entities.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketBuilderTest {

    private Builder ticketBuilder;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        ticketBuilder = new Builder(modelMapper);
    }

    @Test
    void givenValidTicketDTO_whenBuild_thenReturnsTicket() {
        Date start = new Date();
        Date finish = new Date();
        Date paid = new Date();
        TicketDTO ticketDTO = new TicketDTO(start, finish, 50.0, paid, 100L);

        Ticket ticket = ticketBuilder.build(ticketDTO);

        assertEquals(start, ticket.getStart());
        assertEquals(finish, ticket.getFinish());
        assertEquals(50.0, ticket.getPrice());
        assertEquals(paid, ticket.getPaid());
        assertEquals(100L, ticket.getLot());
    }

    @Test
    void givenValidTicket_whenBuild_thenReturnsTicketDTO() {
        Date start = new Date();
        Date finish = new Date();
        Date paid = new Date();
        Ticket ticket = new Ticket(1L, start, finish, 50.0, paid, 100L);

        Optional<TicketDTO> ticketDTOOptional = ticketBuilder.build(ticket);

        assertTrue(ticketDTOOptional.isPresent());
        TicketDTO ticketDTO = ticketDTOOptional.get();

        assertEquals(start, ticketDTO.getStart());
        assertEquals(finish, ticketDTO.getFinish());
        assertEquals(50.0, ticketDTO.getPrice());
        assertEquals(paid, ticket.getPaid());
        assertEquals(100L, ticketDTO.getLot());
    }

    @Test
    void givenValidTicketDTOAndExistingTicket_whenBuild_thenUpdatesTicket() {
        Date initialStart = new Date();
        Date initialFinish = new Date();
        Ticket existingTicket = new Ticket(1L, initialStart, initialFinish, 30.0, null, 100L);

        Date newStart = new Date();
        Date newFinish = new Date();
        Date newPaid = new Date();
        TicketDTO ticketDTO = new TicketDTO(newStart, newFinish, 60.0, newPaid, 100L);

        Ticket updatedTicket = ticketBuilder.build(ticketDTO, existingTicket);

        assertEquals(newStart, updatedTicket.getStart());
        assertEquals(newFinish, updatedTicket.getFinish());
        assertEquals(60.0, updatedTicket.getPrice());
        assertEquals(newPaid, updatedTicket.getPaid());
        assertEquals(100L, updatedTicket.getLot());
    }
}