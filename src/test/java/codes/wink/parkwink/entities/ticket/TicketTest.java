package codes.wink.parkwink.entities.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStart(new Date());
        ticket.setFinish(new Date());
        ticket.setPrice(10.0);
        ticket.setPaid(null);
        ticket.setLot(100L);
    }

    @Test
    void givenNewValues_whenSettersCalled_thenGettersReturnUpdatedValues() {
        Date newStart = new Date();
        Date newFinish = new Date();
        Date newPaid = new Date();
        ticket.setStart(newStart);
        ticket.setFinish(newFinish);
        ticket.setPrice(20.0);
        ticket.setPaid(newPaid);
        ticket.setLot(200L);

        assertEquals(1L, ticket.getId());
        assertEquals(newStart, ticket.getStart());
        assertEquals(newFinish, ticket.getFinish());
        assertEquals(20.0, ticket.getPrice());
        assertEquals(newPaid, ticket.getPaid());
        assertEquals(200L, ticket.getLot());
    }

}
