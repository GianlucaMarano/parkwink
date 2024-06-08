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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service class for handling parking ticket operations.
 * Provides methods for creating, updating, deleting, and retrieving parking ticket information.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Service - Indicates that this class is a Spring service component.</li>
 * </ul>
 * </p>
 */
@Service("TicketService")
public class TicketService {

    /**
     * Repository for accessing parking ticket data.
     */
    private final TicketRepository repo;

    /**
     * Repository for accessing parking lot data.
     */
    private final LotRepository lotRepo;

    /**
     * Builder for converting between Ticket and TicketDTO objects.
     */
    private final Builder builder;

    /**
     * Constructs a TicketService with the specified TicketRepository, LotRepository, and Builder.
     *
     * @param repo    the TicketRepository for accessing parking ticket data.
     * @param builder the Builder for converting between Ticket and TicketDTO objects.
     * @param lotRepo the LotRepository for accessing parking lot data.
     */
    public TicketService(TicketRepository repo, Builder builder, LotRepository lotRepo) {
        this.repo = repo;
        this.builder = builder;
        this.lotRepo = lotRepo;
    }

    /**
     * Retrieves all parking tickets.
     *
     * @return a list of all parking tickets.
     */
    public List<Ticket> findAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a specific parking ticket by its ID.
     *
     * @param id the ID of the parking ticket to retrieve.
     * @return the requested parking ticket.
     * @throws NoSuchElementException if no parking ticket is found with the specified ID.
     */
    public Ticket findOne(Long id) {
        Optional<Ticket> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Ticket was found at the ID you requested");
        }
        return found.get();
    }

    /**
     * Creates a new parking ticket.
     *
     * @param dto the TicketDTO containing the details of the parking ticket to create.
     * @return the created parking ticket.
     * @throws NoFreeLotAvailableException if no free parking lot is available.
     * @throws IllegalArgumentException    if the provided data is invalid.
     * @throws NullPointerException        if the provided data is null.
     */
    public Ticket create(TicketDTO dto) throws NoFreeLotAvailableException, IllegalArgumentException, NullPointerException {
        findFreeLot(dto);
        return Stream.of(dto)
                .map(builder::build)
                .map(repo::save)
                .findFirst()
                .get();
    }

    /**
     * Finds a free parking lot and assigns it to the ticket.
     *
     * @param dto the TicketDTO to update with the assigned parking lot.
     * @throws NoFreeLotAvailableException if no free parking lot is available.
     */
    private void findFreeLot(TicketDTO dto) throws NoFreeLotAvailableException {
        Optional<Lot> freeLot = lotRepo.findOneFree();
        if (freeLot.isPresent()) {
            dto.setLot(freeLot.get().getId());
            freeLot.get().setBusy(true);
            lotRepo.save(freeLot.get());
        } else {
            throw new NoFreeLotAvailableException("No free lot available");
        }
    }

    /**
     * Sets the parking lot to free.
     *
     * @param ticket the Ticket associated with the parking lot to be freed.
     */
    private void setFreeLot(Ticket ticket) {
        Optional<Lot> lot = lotRepo.findById(ticket.getLot());
        if (lot.isPresent()) {
            lot.get().setBusy(false);
            lotRepo.save(lot.get());
        }
    }

    /**
     * Updates an existing parking ticket.
     *
     * @param id         the ID of the parking ticket to update.
     * @param newDetails the TicketDTO containing the new details of the parking ticket.
     * @return the updated parking ticket.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     * @throws NoSuchElementException   if no parking ticket is found with the specified ID.
     */
    @Transactional
    public Ticket update(Long id, TicketDTO newDetails) throws IllegalArgumentException, NullPointerException {
        Optional<Ticket> found = repo.findById(id)
                .map(model -> builder.build(newDetails, model))
                .map(repo::save)
                .map(builder::build)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Ticket was found at ID '%s'", id)))
                .map(builder::build);
        found.get().setId(id);
        return found.get();
    }

    /**
     * Deletes a specific parking ticket by its ID.
     *
     * @param id the ID of the parking ticket to delete.
     * @return a confirmation message.
     * @throws NoSuchElementException if no parking ticket is found with the specified ID.
     */
    public String delete(Long id) {
        Optional<Ticket> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Ticket was found at the ID you requested");
        }
        repo.deleteById(id);
        return "Ticket deleted successfully";
    }

    /**
     * Ends a parking session for a specific ticket.
     *
     * @param id the ID of the parking ticket to end.
     * @return the updated parking ticket with end time and calculated price.
     * @throws NoSuchElementException if no parking ticket is found with the specified ID.
     */
    public Ticket end(Long id) {
        Optional<Ticket> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Ticket was found at the ID you requested");
        }
        Ticket newDetails = found.get();
        newDetails.setFinish(new Date());
        long hours = (newDetails.getFinish().getTime() - newDetails.getStart().getTime()) / (60 * 60 * 1000);
        double price = 1 + hours * 0.90;
        newDetails.setPrice(price);
        found.map(model -> builder.build(newDetails, model))
                .map(repo::save)
                .map(builder::build)
                .orElseThrow(() -> new NoSuchElementException(String.format("No Ticket was found at ID '%s'", id)))
                .map(builder::build);
        found.get().setId(id);
        return found.get();
    }

    /**
     * Marks a specific parking ticket as paid.
     *
     * @param id the ID of the parking ticket to mark as paid.
     * @return the updated parking ticket with payment time set.
     * @throws PaymentExpiredException  if the payment is attempted more than 10 minutes after the parking session has ended.
     * @throws ParkingNotEndedException if an attempt is made to mark the ticket as paid before the parking session has ended.
     * @throws IllegalArgumentException if the provided data is invalid.
     * @throws NullPointerException     if the provided data is null.
     * @throws NoSuchElementException   if no parking ticket is found with the specified ID.
     */
    public Ticket paid(Long id) throws PaymentExpiredException, ParkingNotEndedException, IllegalArgumentException, NullPointerException {
        Optional<Ticket> found = repo.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException("No Ticket was found at the ID you requested");
        }
        if (found.get().getFinish() != null) {
            long minutes = (new Date().getTime() - found.get().getFinish().getTime()) / (60 * 1000);
            if (minutes > 10) {
                found.get().setFinish(new Date());
                repo.save(found.get());
                throw new PaymentExpiredException("Payment has required too much time, retry");
            }
        } else {
            throw new ParkingNotEndedException("Cannot set paid if parking is not ended");
        }
        found.get().setPaid(new Date());
        setFreeLot(found.get());
        return repo.save(found.get());
    }
}
