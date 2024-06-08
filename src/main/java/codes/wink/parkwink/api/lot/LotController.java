package codes.wink.parkwink.api.lot;

import codes.wink.parkwink.entities.lot.Lot;
import codes.wink.parkwink.entities.lot.LotDTO;
import codes.wink.parkwink.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing parking lots.
 * Provides endpoints for creating, updating, deleting, and retrieving parking lot information.
 *
 * <p>Annotations:
 * <ul>
 * <li>@RestController - Indicates that this class is a REST controller.</li>
 * <li>@CrossOrigin - Allows cross-origin requests.</li>
 * <li>@RequestMapping - Specifies the base URL for all endpoints in this controller.</li>
 * </ul>
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/lot")
public class LotController {

    /**
     * Service for handling parking lot operations.
     */
    private final LotService service;

    /**
     * Constructs a LotController with the specified LotService.
     *
     * @param service the LotService to be used for parking lot operations.
     */
    @Autowired
    public LotController(LotService service) {
        this.service = service;
    }

    /**
     * Retrieves all parking lots.
     *
     * @return a Response object containing a list of all parking lots.
     */
    @GetMapping("/")
    public Response<List<Lot>> all() {
        return new Response<>(service.findAll());
    }

    /**
     * Retrieves a specific parking lot by its ID.
     *
     * @param id the ID of the parking lot to retrieve.
     * @return a Response object containing the requested parking lot.
     */
    @GetMapping("/{id}")
    public Response<Lot> findOne(@PathVariable("id") String id) {
        return new Response<>(service.findOne(Long.parseLong(id)));
    }

    /**
     * Creates a new parking lot.
     *
     * @param dto the LotDTO containing the details of the parking lot to create.
     * @return a Response object containing the created parking lot and HTTP status.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public Response<Lot> create(@Valid @RequestBody LotDTO dto)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.create(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing parking lot.
     *
     * @param id         the ID of the parking lot to update.
     * @param newDetails the LotDTO containing the new details of the parking lot.
     * @return a Response object containing the updated parking lot.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public Response<Lot> update(@PathVariable("id") String id, @Valid @RequestBody LotDTO newDetails)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.update(Long.parseLong(id), newDetails));
    }

    /**
     * Deletes a specific parking lot by its ID.
     *
     * @param id the ID of the parking lot to delete.
     * @return a Response object containing a confirmation message.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") String id) {
        return new Response<>(service.delete(Long.parseLong(id)));
    }
}
