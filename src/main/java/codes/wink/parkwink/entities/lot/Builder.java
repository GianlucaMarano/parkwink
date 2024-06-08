package codes.wink.parkwink.entities.lot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Builder component for converting between Lot and LotDTO objects.
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
@Component("PostoBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Builder {

    /**
     * ModelMapper instance for mapping between DTO and entity objects.
     */
    @Autowired
    private ModelMapper mapper;

    /**
     * Converts a LotDTO object to a Lot entity.
     *
     * @param dto the LotDTO object to convert.
     * @return the resulting Lot entity.
     */
    public Lot build(LotDTO dto) {
        return mapper.map(dto, Lot.class);
    }

    /**
     * Converts a Lot entity to a LotDTO object.
     *
     * @param domain the Lot entity to convert.
     * @return an Optional containing the resulting LotDTO object.
     */
    public Optional<LotDTO> build(Lot domain) {
        LotDTO dto = mapper.map(domain, LotDTO.class);
        return Optional.of(dto);
    }

    /**
     * Updates an existing Lot entity with values from a LotDTO object.
     *
     * @param dto    the LotDTO object containing updated values.
     * @param domain the Lot entity to update.
     * @return the updated Lot entity.
     */
    public Lot build(LotDTO dto, Lot domain) {
        mapper.map(dto, domain);
        return domain;
    }
}
