package com.module1.dao;

import com.module1.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TicketRepository {

    private Storage storage;

    public Ticket update(Ticket ticket) {
        storage.getTickets().put(ticket.getId(), ticket);
        return new Ticket(ticket);
    }

    public Optional<Ticket> getTicketForEvent(Long eventId, int place, Ticket.Category category) {
        return storage.getTickets().values()
                .stream()
                .filter(t -> doesTicketExistForEvent(t, eventId, place, category))
                .map(Ticket::new)
                .findFirst();
    }

    private boolean doesTicketExistForEvent(Ticket ticket, Long eventId, int place, Ticket.Category category) {
        return ticket.getEventId().equals(eventId) && ticket.getSitingNumber() == place && ticket.getCategory() == category;
    }

    public List<Ticket> getBookedTicketsForEvent(Long eventId) {
        return storage.getTickets()
                .values()
                .stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .filter(ticket -> ticket.getUserId() != null)
                .map(Ticket::new)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTicketsForUser(Long userId) {
        return storage.getTickets()
                .values()
                .stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .map(Ticket::new)
                .collect(Collectors.toList());
    }

    public boolean cancelTicket(long ticketId) {
        Ticket ticket = storage.getTickets()
                .get(ticketId);
        if (ticket.getUserId() == null) {
            return false;
        } else {
            ticket.setUserId(null);
            return true;
        }
    }

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }
}
