package com.module1.service;

import com.module1.dao.TicketRepository;
import com.module1.model.Event;
import com.module1.model.Ticket;
import com.module1.model.User;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Setter
class TicketServiceImpl implements TicketService {

    private static final Log LOGGER = LogFactory.getLog(TicketService.class);
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(long userId, Long eventId, int place, Ticket.Category category) {
        LOGGER.info("Booking process started...");
        Ticket ticket = getTicketFromRepo(eventId, place, category);
        if (isPlaceForEventBooked(ticket)) {
            throw new IllegalStateException("This place " + place + " has already been booked");
        }
        ticket.setUserId(userId);
        LOGGER.info("Ticket with id: " + ticket.getId() + " was booked for user with id: " + userId);
        return ticketRepository.update(ticket);
    }

    private Ticket getTicketFromRepo(long eventId, int place, Ticket.Category category) {
        return ticketRepository.getTicketForEvent(eventId, place, category)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no: " + place + " not exists for event with id: " + eventId + " and for category: " + category));

    }

    private boolean isPlaceForEventBooked(Ticket ticket) {
        return ticket.getUserId() != null;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        LOGGER.info("Get booked tickets for user: " + user.getId());
        return ticketRepository.getBookedTicketsForUser(user.getId());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        LOGGER.info("Get booked tickets for event: " + event.getId());
        return ticketRepository.getBookedTicketsForEvent(event.getId());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        LOGGER.info("Cancel ticket with id: " + ticketId);
        return ticketRepository.cancelTicket(ticketId);
    }
}
