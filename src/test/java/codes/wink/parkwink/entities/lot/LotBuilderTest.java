package codes.wink.parkwink.entities.lot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LotBuilderTest {

    private Builder lotBuilder;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        lotBuilder = new Builder(modelMapper);
    }

    @Test
    void givenValidLotDTO_whenBuild_thenReturnsLot() {
        LotDTO lotDTO = new LotDTO(true);

        Lot lot = lotBuilder.build(lotDTO);

        assertEquals(lotDTO.getBusy(), lot.getBusy());
    }

    @Test
    void givenValidLot_whenBuild_thenReturnsLotDTO() {
        Lot lot = new Lot();
        lot.setBusy(true);

        Optional<LotDTO> lotDTOOptional = lotBuilder.build(lot);

        assertTrue(lotDTOOptional.isPresent());
        LotDTO lotDTO = lotDTOOptional.get();

        assertEquals(lot.getBusy(), lotDTO.getBusy());
    }

    @Test
    void givenValidLotDTOAndExistingLot_whenBuild_thenUpdatesLot() {
        Lot existingLot = new Lot();
        existingLot.setBusy(false);

        LotDTO lotDTO = new LotDTO(true);

        Lot updatedLot = lotBuilder.build(lotDTO, existingLot);

        assertEquals(lotDTO.getBusy(), updatedLot.getBusy());
    }
}