package implementation;

import api.EventService;
import dto.Event;
import dto.EventType;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    //    private EventRepository eventRepository;
    private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class);

    @Override
    public Event createEvent(Event event) {
//        Event createdEvent = eventRepository.save(event);
        Event createdEvent = new Event();
        LOGGER.info("Event with id: " + createdEvent.getId() + " created");
        return createdEvent;
    }

    @Override
    public Event updateEvent(Event eventToUpdate) {
//        Event updatedEvent = eventRepository.save(eventToUpdate);
        Event updatedEvent = new Event();

        LOGGER.info("Event with id: " + eventToUpdate.getId() + " updated");
        return updatedEvent;
    }

    @Override
    public Event getEvent(Long id) {
        LOGGER.info("Get event with id: " + id);
        return new Event();
//        return eventRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Event with given Id not exist"));

    }

    @Override
    public void deleteEvent(Long id) {
//        if (eventRepository.existsById(id)) {
//            eventRepository.deleteById(id);
//            LOGGER.info("Deleting event with id: " + id);
//        } else {
//            throw new IllegalStateException("Event with given id not exist");
//        }
    }

    @Override
    public List<Event> getAllEvents() {
//        return StreamSupport.stream(eventRepository.findAll().spliterator(), false).toList();
        return List.of(Event.builder()
                .eventType(EventType.SHOW)
                .title("GetAll")
                .place("Theatre")
                .speaker("Speaker1")
                .dateTime(LocalDateTime.of(2023, Month.APRIL, 20, 20, 0))
                .build());
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
//        return eventRepository.findAllByTitle(title);
        return List.of(Event.builder()
                .eventType(EventType.SHOW)
                .title("ByTitle")
                .place("Theatre")
                .speaker("Speaker1")
                .dateTime(LocalDateTime.of(2023, Month.APRIL, 20, 20, 0))
                .build());
    }
}
