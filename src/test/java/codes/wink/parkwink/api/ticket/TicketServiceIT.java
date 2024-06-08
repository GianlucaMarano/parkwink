package codes.wink.parkwink.api.ticket;

import codes.wink.parkwink.config.customExceptions.NoFreeLotAvailableException;
import codes.wink.parkwink.config.customExceptions.ParkingNotEndedException;
import codes.wink.parkwink.config.customExceptions.PaymentExpiredException;
import codes.wink.parkwink.entities.lot.Lot;
import codes.wink.parkwink.entities.lot.LotRepository;
import codes.wink.parkwink.entities.ticket.Builder;
import codes.wink.parkwink.entities.ticket.Ticket;
import codes.wink.parkwink.entities.ticket.TicketDTO;
import codes.wink.parkwink.entities.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({TicketService.class, Builder.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketServiceIT {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Builder builder;

    private TicketDTO ticketDTO;

    @BeforeEach
    void setUp() {
        Lot lot = new Lot();
        lot.setBusy(false);
        lotRepository.save(lot);

        ticketDTO = new TicketDTO();
        ticketDTO.setStart(new Date());
        ticketDTO.setLot(lot.getId());
    }

    @Test
    @Transactional
    void shouldCreateTicketWhenFreeLotIsAvailable() throws NoFreeLotAvailableException {
        Ticket ticket = ticketService.create(ticketDTO);
        assertNotNull(ticket.getId());
    }

    @Test
    @Transactional
    void shouldFindTicketById() {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        Ticket foundTicket = ticketService.findOne(savedTicket.getId());
        assertEquals(savedTicket.getId(), foundTicket.getId());
    }

    @Test
    @Transactional
    void shouldDeleteTicketById() {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        String result = ticketService.delete(savedTicket.getId());
        assertEquals("Ticket deleted successfully", result);
        assertTrue(ticketRepository.findById(savedTicket.getId()).isEmpty());
    }

    @Test
    @Transactional
    void shouldEndTicketAndCalculatePrice() {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        Ticket endedTicket = ticketService.end(savedTicket.getId());
        assertNotNull(endedTicket.getFinish());
        assertNotNull(endedTicket.getPrice());
    }

    @Test
    @Transactional
    void shouldMarkTicketAsPaidIfPaymentIsWithinTimeLimit() throws PaymentExpiredException, ParkingNotEndedException {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        savedTicket.setFinish(new Date());
        ticketRepository.save(savedTicket);

        Ticket paidTicket = ticketService.paid(savedTicket.getId());
        assertNotNull(paidTicket.getPaid());
        assertFalse(lotRepository.findById(savedTicket.getLot()).get().getBusy());
    }

    @Test
    @Transactional
    void shouldThrowPaymentExpiredExceptionIfPaymentIsDelayed() {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        Date finishDate = new Date(System.currentTimeMillis() - (11 * 60 * 1000)); // 11 minutes ago
        savedTicket.setFinish(finishDate);
        ticketRepository.save(savedTicket);

        assertThrows(PaymentExpiredException.class, () -> ticketService.paid(savedTicket.getId()));
    }

    @Test
    void shouldThrowParkingNotEndedExceptionIfParkingIsNotEnded() {
        Ticket savedTicket = ticketRepository.save(builder.build(ticketDTO));
        assertThrows(ParkingNotEndedException.class, () -> ticketService.paid(savedTicket.getId()));
    }
}
