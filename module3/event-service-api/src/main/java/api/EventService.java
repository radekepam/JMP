package api;

import dto.Event;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EventService {
    Event createEvent(Event eventToSave);
    Event updateEvent(Event eventToUpdate);
    Event getEvent(Long id);
    void deleteEvent(Long id);
    List<Event> getAllEvents();
    List<Event> getAllEventsByTitle(String title);
}
