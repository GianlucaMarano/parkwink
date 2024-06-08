package codes.wink.parkwink.entities.lot;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class LotRepositoryIT {

    @Autowired
    private LotRepository lotRepository;

    private Lot busyLot;
    private Lot freeLot;

    @BeforeEach
    void setUp() {
        busyLot = new Lot();
        busyLot.setBusy(true);

        freeLot = new Lot();
        freeLot.setBusy(false);

        lotRepository.save(busyLot);
        lotRepository.save(freeLot);
    }

    @Test
    void givenExistingLotId_whenFindById_thenLotIsFound() {
        Optional<Lot> foundLot = lotRepository.findById(busyLot.getId());
        assertTrue(foundLot.isPresent());
    }

    @Test
    void givenExistingLotId_whenFindById_thenCorrectLotIsReturned() {
        Optional<Lot> foundLot = lotRepository.findById(busyLot.getId());
        assertTrue(foundLot.isPresent());
        assertEquals(busyLot.getId(), foundLot.get().getId());
    }

    @Test
    void givenNonExistingLotId_whenFindById_thenReturnsEmptyOptional() {
        Optional<Lot> foundLot = lotRepository.findById(999L);
        assertFalse(foundLot.isPresent());
    }

    @Test
    void whenFindAll_thenReturnsAllLots() {
        List<Lot> lots = lotRepository.findAll();
        assertEquals(2, lots.size());
    }

    @Test
    void whenFindAll_thenContainsSavedLots() {
        List<Lot> lots = lotRepository.findAll();
        assertTrue(lots.contains(busyLot));
        assertTrue(lots.contains(freeLot));
    }

    @Test
    void givenExistingLot_whenSave_thenLotIsUpdated() {
        busyLot.setBusy(false);
        lotRepository.save(busyLot);
        Optional<Lot> updatedLot = lotRepository.findById(busyLot.getId());
        assertTrue(updatedLot.isPresent());
        assertFalse(updatedLot.get().getBusy());
    }

    @Test
    void givenNewLot_whenSave_thenLotIsCreated() {
        Lot newLot = new Lot();
        newLot.setBusy(false);

        Lot savedLot = lotRepository.save(newLot);

        Optional<Lot> foundLot = lotRepository.findById(savedLot.getId());
        assertTrue(foundLot.isPresent());
    }

    @Test
    void givenExistingLotId_whenDeleteById_thenLotIsRemoved() {
        lotRepository.deleteById(busyLot.getId());
        Optional<Lot> foundLot = lotRepository.findById(busyLot.getId());
        assertFalse(foundLot.isPresent());
    }

    @Test
    void givenFreeLot_whenFindOneFree_thenReturnsFreeLot() {
        Optional<Lot> foundLot = lotRepository.findOneFree();
        assertTrue(foundLot.isPresent());
        assertEquals(freeLot.getId(), foundLot.get().getId());
    }

    @Test
    void givenNoFreeLot_whenFindOneFree_thenReturnsEmptyOptional() {
        lotRepository.delete(freeLot);
        Optional<Lot> foundLot = lotRepository.findOneFree();
        assertFalse(foundLot.isPresent());
    }
}
