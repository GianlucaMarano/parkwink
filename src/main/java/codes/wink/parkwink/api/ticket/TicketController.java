package codes.wink.parkwink.api.ticket;

import codes.wink.parkwink.config.customExceptions.NoFreeLotAvailableException;
import codes.wink.parkwink.config.customExceptions.ParkingNotEndedException;
import codes.wink.parkwink.config.customExceptions.PaymentExpiredException;
import codes.wink.parkwink.entities.ticket.Ticket;
import codes.wink.parkwink.entities.ticket.TicketDTO;
import codes.wink.parkwink.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing parking tickets.
 * Provides endpoints for creating, updating, deleting, and retrieving parking ticket information.
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
@RequestMapping("/api/v1/ticket")
public class TicketController {

    /**
     * Service for handling parking ticket operations.
     */
    private final TicketService service;

    /**
     * Constructs a TicketController with the specified TicketService.
     *
     * @param service the TicketService to be used for parking ticket operations.
     */
    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    /**
     * Retrieves all parking tickets.
     *
     * @return a Response object containing a list of all parking tickets.
     */
    @GetMapping("/")
    public Response<List<Ticket>> all() {
        return new Response<>(service.findAll());
    }

    /**
     * Retrieves a specific parking ticket by its ID.
     *
     * @param id the ID of the parking ticket to retrieve.
     * @return a Response object containing the requested parking ticket.
     */
    @GetMapping("/{id}")
    public Response<Ticket> findOne(@PathVariable("id") String id) {
        return new Response<>(service.findOne(Long.parseLong(id)));
    }

    /**
     * Creates a new parking ticket.
     *
     * @param dto the TicketDTO containing the details of the parking ticket to create.
     * @return a Response object containing the created parking ticket and HTTP status.
     * @throws IllegalArgumentException    if the provided data is invalid.
     * @throws NullPointerException        if the provided data is null.
     * @throws NoFreeLotAvailableException if no free parking lot is available.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public Response<Ticket> create(@Valid @RequestBody TicketDTO dto)
            throws IllegalArgumentException, NullPointerException, NoFreeLotAvailableException {
        return new Response<>(service.create(dto), HttpStatus.CREATED);
    }

    /**
     * Ends a parking session for a specific ticket.
     *
     * @param id the ID of the parking ticket to end.
     * @return a Response object containing the updated parking ticket.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/end")
    public Response<Ticket> end(@PathVariable("id") String id)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.end(Long.parseLong(id)));
    }

    /**
     * Marks a specific parking ticket as paid.
     *
     * @param id the ID of the parking ticket to mark as paid.
     * @return a Response object containing the updated parking ticket.
     * @throws PaymentExpiredException  if the payment is attempted more than 10 minutes after the parking session has ended.
     * @throws ParkingNotEndedException if an attempt is made to mark the ticket as paid before the parking session has ended.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/paid")
    public Response<Ticket> paid(@PathVariable("id") String id)
            throws PaymentExpiredException, ParkingNotEndedException, IllegalArgumentException, NullPointerException {
        return new Response<>(service.paid(Long.parseLong(id)));
    }

    /**
     * Updates an existing parking ticket.
     *
     * @param id         the ID of the parking ticket to update.
     * @param newDetails the TicketDTO containing the new details of the parking ticket.
     * @return a Response object containing the updated parking ticket.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public Response<Ticket> update(@PathVariable("id") String id, @Valid @RequestBody TicketDTO newDetails)
            throws IllegalArgumentException, NullPointerException {
        return new Response<>(service.update(Long.parseLong(id), newDetails));
    }

    /**
     * Deletes a specific parking ticket by its ID.
     *
     * @param id the ID of the parking ticket to delete.
     * @return a Response object containing a confirmation message.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable("id") String id) {
        return new Response<>(service.delete(Long.parseLong(id)));
    }
}
