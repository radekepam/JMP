package com.module1.dao;

import com.module1.model.Event;
import com.module1.model.Ticket;
import com.module1.model.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Storage {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Ticket> tickets = new HashMap<>();
    private final Map<Long, Event> events = new HashMap<>();

    public Storage() {

    }

    public void initialize(List<String> lines) {
        insertUsersFromFile(lines);
        insertEventsFromFile(lines);
        generateTickets();
    }

    private void insertUsersFromFile(List<String> lines) {
        for (String line: lines) {
            if (line.equals("USERS")) {
                continue;
            } else if (line.equals("EVENTS")) {
                break;
            }
            String[] user = line.split(",");
            this.users.put(Long.parseLong(user[0].trim()), new User(Long.parseLong(user[0].trim()), user[1].trim(), user[2].trim()));
        }
    }

    private void insertEventsFromFile(List<String> lines) {
        boolean isEvents = false;
        for(String line: lines) {
            if(line.equals("EVENTS")) {
                isEvents = true;
                continue;
            }
            if(!isEvents) {
                continue;
            }
            String[] event = line.split(",");
            this.events.put(Long.parseLong(event[0].trim()), new Event(Long.parseLong(event[0].trim()), event[1].trim(), LocalDateTime.parse(event[2].trim()), Integer.parseInt(event[3].trim())));
        }
    }

    private void generateTickets() {
        events.values()
                .forEach(event -> generateTicketsForEvent(event.getId(), event.getSittings()));
    }

    private void generateTicketsForEvent(long eventId, int ticketQuantity) {
        long nextId = tickets.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
        for (long i = nextId; i < ticketQuantity + nextId; i++) {
            if (i % 50 == 0) {
                this.tickets.put(i, new Ticket(i, eventId, null, (int) i - (int) nextId, Ticket.Category.PREMIUM));
            }
            this.tickets.put(i, new Ticket(i, eventId, null, (int) i - (int) nextId, Ticket.Category.COMMON));
        }
    }
}
