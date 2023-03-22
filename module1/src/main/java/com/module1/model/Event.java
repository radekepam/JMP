package com.module1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Event {

    private Long id;
    private String title;
    private LocalDateTime startDate;
    private int sittings;

    public Event(String title, LocalDateTime startDate, int sittings) {
        this.title = title;
        this.startDate = startDate;
        this.sittings = sittings;
    }

    public Event(Event event) {
        this(event.id, event.title, event.startDate, event.sittings);
    }
}
