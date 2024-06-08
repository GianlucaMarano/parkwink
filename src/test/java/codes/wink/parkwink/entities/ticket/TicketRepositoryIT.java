package codes.wink.parkwink.entities.ticket;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class TicketRepositoryIT {

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeEach
    void setUp() {
        ticket1 = new Ticket();
        ticket1.setStart(new Date());
        ticket1.setFinish(new Date());
        ticket1.setPrice(10.0);
        ticket1.setPaid(null);
        ticket1.setLot(100L);

        ticket2 = new Ticket();
        ticket2.setStart(new Date());
        ticket2.setFinish(new Date());
        ticket2.setPrice(20.0);
        ticket2.setPaid(new Date());
        ticket2.setLot(200L);

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
    }

    @Test
    void givenExistingTicketId_whenFindById_thenTicketIsFound() {
        Optional<Ticket> foundTicket = ticketRepository.findById(ticket1.getId());
        assertTrue(foundTicket.isPresent());
    }

    @Test
    void givenExistingTicketId_whenFindById_thenCorrectTicketIsReturned() {
        Optional<Ticket> foundTicket = ticketRepository.findById(ticket1.getId());
        assertTrue(foundTicket.isPresent());
        assertEquals(ticket1.getId(), foundTicket.get().getId());
    }

    @Test
    void givenNonExistingTicketId_whenFindById_thenReturnsEmptyOptional() {
        Optional<Ticket> foundTicket = ticketRepository.findById(999L);
        assertFalse(foundTicket.isPresent());
    }

    @Test
    void whenFindAll_thenReturnsAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        assertEquals(2, tickets.size());
    }

    @Test
    void whenFindAll_thenContainsSavedTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        assertTrue(tickets.contains(ticket1));
        assertTrue(tickets.contains(ticket2));
    }

    @Test
    void givenExistingTicket_whenSave_thenTicketIsUpdated() {
        ticket1.setPrice(15.0);
        ticketRepository.save(ticket1);
        Optional<Ticket> updatedTicket = ticketRepository.findById(ticket1.getId());
        assertTrue(updatedTicket.isPresent());
        assertEquals(15.0, updatedTicket.get().getPrice());
    }

    @Test
    void givenNewTicket_whenSave_thenTicketIsCreated() {
        Ticket newTicket = new Ticket();
        newTicket.setStart(new Date());
        newTicket.setFinish(new Date());
        newTicket.setPrice(30.0);
        newTicket.setPaid(null);
        newTicket.setLot(300L);

        Ticket savedTicket = ticketRepository.save(newTicket);

        Optional<Ticket> foundTicket = ticketRepository.findById(savedTicket.getId());
        assertTrue(foundTicket.isPresent());
    }

    @Test
    void givenExistingTicketId_whenDeleteById_thenTicketIsRemoved() {
        ticketRepository.deleteById(ticket1.getId());
        Optional<Ticket> foundTicket = ticketRepository.findById(ticket1.getId());
        assertFalse(foundTicket.isPresent());
    }
}
