package com.example.module3.eventServiceRest.controller;

import com.example.module3.eventServiceApi.api.EventService;
import com.example.module3.eventServiceDto.dto.Event;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventController {

    private EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable("eventId") Long eventId) {
        Event event = eventService.getEvent(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(name = "title", required = false) String title) {
        if(Objects.nonNull(title)) {
            return ResponseEntity.ok(eventService.getAllEventsByTitle(title));
        } else {
            return ResponseEntity.ok(eventService.getAllEvents());
        }
    }
    @DeleteMapping("/eventId")
    public ResponseEntity<Object> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity
                .created(URI.create("/api/events/" + createdEvent.getId()))
                .build();
    }

    @PutMapping("/eventId")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(updatedEvent);

    }
}
