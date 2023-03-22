package com.module1.dao;

import com.module1.model.Event;
import com.module1.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventRepository {

    private Storage storage;

    public Optional<Event> getEventById(long eventId) {
        return Optional.ofNullable(storage.getEvents().get(eventId));
    }

    public List<Event> getEventsByTitle(String title) {
        return storage.getEvents().values()
                .stream()
                .filter(e -> e.getTitle().toLowerCase().contains(title.toLowerCase()))
                .map(Event::new)
                .collect(Collectors.toList());
    }

    public List<Event> getEventsForDay(LocalDate day) {
        return storage.getEvents().values()
                .stream()
                .filter(event -> event.getStartDate().toLocalDate().equals(day))
                .collect(Collectors.toList());
    }

    public Event createEvent(Event event) {
        long newEventId = generateNewEventId();
        event.setId(newEventId);
        storage.getEvents().put(newEventId, event);
        generateTicketsForEvent(event);
        return new Event(event);
    }

    private long generateNewEventId() {
        return storage.getEvents()
                .values()
                .stream()
                .map(Event::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    private void generateTicketsForEvent(Event event) {
        Long idBeginning = generateNewTicketsIdsStartingPoint();
        for (int i = 1; i <= event.getSittings(); i++) {
            storage.getTickets().put(idBeginning + i, new Ticket(idBeginning + i, event.getId(), null, i, Ticket.Category.COMMON));
        }
    }

    private Long generateNewTicketsIdsStartingPoint() {
        return storage.getTickets().keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public Event updateEvent(Event event) {
        storage.getEvents().put(event.getId(), event);
        return new Event(event);
    }

    public boolean deleteById(long eventId) {
        removeTicketsForEvent(eventId);
        return Optional.ofNullable(storage.getEvents().remove(eventId))
                .isPresent();
    }

    private void removeTicketsForEvent(Long eventId) {
        Map<Long, Ticket> tickets = storage.getTickets();
        List<Long> collect = tickets
                .entrySet()
                .stream()
                .filter(e -> e.getValue().getEventId().equals(eventId))
                .map(Map.Entry::getKey)
                .toList();
        collect.forEach(tickets::remove);
    }

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
