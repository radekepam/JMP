package com.module1.service;


import com.module1.dao.EventRepository;
import com.module1.model.Event;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Setter
class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LogManager.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event getEventById(long eventId) {
        LOGGER.info("Get event with id: " + eventId);
        return eventRepository.getEventById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event with given Id not exist"));
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        //todo PageSize and PageNum are useless when we want to return list instead Pageable
        LOGGER.info("Get events contains title: " + title);
        return eventRepository.getEventsByTitle(title);
    }

    @Override
    public List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum) {
        LOGGER.info("Get events for dat: " + day.toString());
        return eventRepository.getEventsForDay(day);
    }

    @Override
    public Event createEvent(Event event) {
        Event createdEvent = eventRepository.createEvent(event);
        LOGGER.info("Event with id: " + createdEvent.getId() + " created");
        return createdEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        LOGGER.info("Event with id: " + event.getId() + " updated");
        return eventRepository.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        boolean isDeleted = eventRepository.deleteById(eventId);
        LOGGER.info("Deleting event with id: " + eventId + " ended with successfully - " + isDeleted);
        return isDeleted;
    }
}
