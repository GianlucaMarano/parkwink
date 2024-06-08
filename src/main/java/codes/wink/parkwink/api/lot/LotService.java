package codes.wink.parkwink.api.lot;

import codes.wink.parkwink.entities.lot.Builder;
import codes.wink.parkwink.entities.lot.Lot;
import codes.wink.parkwink.entities.lot.LotDTO;
import codes.wink.parkwink.entities.lot.LotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service class for handling parking lot operations.
 * Provides methods for creating, updating, deleting, and retrieving parking lot information.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Service - Indicates that this class is a Spring service component.</li>
 * </ul>
 * </p>
 */
@Service("LotService")
public class LotService {

    /**
     * Repository for accessing parking lot data.
     */
    private final LotRepository repo;

    /**
     * Builder for converting between Lot and LotDTO objects.
     */
    private final Builder builder;

    /**
     * Constructs a LotService with the specified LotRepository and Builder.
     *
     * @param repo    the LotRepository for accessing parking lot data.
     * @param builder the Builder for converting between Lot and LotDTO objects.
     */
    public LotService(LotRepository repo, Builder builder) {
        this.repo = repo;
        this.builder = builder;
    }

    /**
     * Retrieves all parking lots.
     *
     * @return a list of all parking lots.
     */
    public List<Lot> findAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a specific parking lot by its ID.
     *
     * @param id the ID of the parking lot to retrieve.
     * @return the requested parking lot.
     * @throws NoSuchElementException if no parking lot is found with the specified ID.
     */
    public Lot findOne(Long id) {
        Optional<Lot> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Lot was found at the ID you requested");
        }
        return found.get();
    }

    /**
     * Creates a new parking lot.
     *
     * @param dto the LotDTO containing the details of the parking lot to create.
     * @return the created parking lot.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    public Lot create(LotDTO dto) throws IllegalArgumentException, NullPointerException {
        return Stream.of(dto)
                .map(builder::build)
                .map(repo::save)
                .findFirst()
                .get();
    }

    /**
     * Updates an existing parking lot.
     *
     * @param id         the ID of the parking lot to update.
     * @param newDetails the LotDTO containing the new details of the parking lot.
     * @return the updated parking lot.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     * @throws NoSuchElementException   if no parking lot is found with the specified ID.
     */
    @Transactional
    public Lot update(Long id, LotDTO newDetails) throws IllegalArgumentException, NullPointerException {
        Optional<Lot> found = repo.findById(id)
                .map(model -> builder.build(newDetails, model))
                .map(repo::save)
                .map(builder::build)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Lot was found at ID '%s'", id)))
                .map(builder::build);
        found.get().setId(id);
        return found.get();
    }

    /**
     * Deletes a specific parking lot by its ID.
     *
     * @param id the ID of the parking lot to delete.
     * @return a confirmation message.
     * @throws NoSuchElementException if no parking lot is found with the specified ID.
     */
    public String delete(Long id) {
        Optional<Lot> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Lot was found at the ID you requested");
        }
        repo.deleteById(id);
        return "Lot deleted successfully";
    }
}
