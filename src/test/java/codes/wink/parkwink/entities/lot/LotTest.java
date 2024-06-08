package codes.wink.parkwink.entities.lot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LotTest {

    private Lot lot;

    @BeforeEach
    void setUp() {
        lot = new Lot();
        lot.setId(1L);
        lot.setBusy(true);
    }

    @Test
    void givenNewValues_whenSettersCalled_thenGettersReturnUpdatedValues() {
        lot.setId(2L);
        lot.setBusy(false);

        assertEquals(2L, lot.getId());
        assertFalse(lot.getBusy());
    }

}
