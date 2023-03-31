package com.example.module3.eventServiceImpl.implementation;


import com.example.module3.eventServiceApi.api.EventService;
import com.example.module3.eventServiceDto.dto.Event;
import com.example.module3.eventServiceDto.dto.EventRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class);

    @Override
    public Event createEvent(Event event) {
        Event createdEvent = eventRepository.save(event);
        LOGGER.info("Event with id: " + createdEvent.getId() + " created");
        return createdEvent;
    }

    @Override
    public Event updateEvent(Event eventToUpdate) {
        Event updatedEvent = eventRepository.save(eventToUpdate);
        LOGGER.info("Event with id: " + eventToUpdate.getId() + " updated");
        return updatedEvent;
    }

    @Override
    public Event getEvent(Long id) {
        LOGGER.info("Get event with id: " + id);
        return eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event with given Id not exist"));
    }

    @Override
    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            LOGGER.info("Deleting event with id: " + id);
        } else {
            throw new IllegalStateException("Event with given id not exist");
        }
    }

    @Override
    public List<Event> getAllEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        return eventRepository.findAllByTitle(title);
    }
}
