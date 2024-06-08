package codes.wink.parkwink.api.lot;

import codes.wink.parkwink.entities.lot.Builder;
import codes.wink.parkwink.entities.lot.Lot;
import codes.wink.parkwink.entities.lot.LotDTO;
import codes.wink.parkwink.entities.lot.LotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({LotService.class, Builder.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class LotServiceIT {

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private LotService lotService;

    @Autowired
    private Builder builder;

    private LotDTO lotDTO;

    @BeforeEach
    void setUp() {
        lotDTO = new LotDTO();
        lotDTO.setBusy(false);
    }

    @Test
    void createLot_ShouldReturnLotWithId() {
        Lot lot = lotService.create(lotDTO);
        assertNotNull(lot.getId());
    }

    @Test
    void findLotById_ShouldReturnCorrectLot() {
        Lot savedLot = lotRepository.save(builder.build(lotDTO));
        Lot foundLot = lotService.findOne(savedLot.getId());
        assertEquals(savedLot.getId(), foundLot.getId());
    }

    @Test
    void deleteLotById_ShouldRemoveLot() {
        Lot savedLot = lotRepository.save(builder.build(lotDTO));
        String result = lotService.delete(savedLot.getId());
        assertEquals("Lot deleted successfully", result);
        assertTrue(lotRepository.findById(savedLot.getId()).isEmpty());
    }

    @Test
    void updateLot_ShouldReturnUpdatedLot() {
        Lot savedLot = lotRepository.save(builder.build(lotDTO));
        LotDTO updatedDTO = new LotDTO();
        updatedDTO.setBusy(true);

        Lot updatedLot = lotService.update(savedLot.getId(), updatedDTO);

        assertEquals(savedLot.getId(), updatedLot.getId());
        assertTrue(updatedLot.getBusy());
    }

    @Test
    void findLotById_WhenLotNotFound_ShouldThrowException() {
        Long nonExistentId = 999L;
        assertThrows(NoSuchElementException.class, () -> lotService.findOne(nonExistentId));
    }
}
