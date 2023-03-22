package com.module1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Ticket {

    private Long id;
    private Long eventId;

    private Long userId;
    private int sitingNumber;
    private Category category;

    public enum Category {
        PREMIUM, COMMON
    }

    public Ticket(Ticket ticket) {
        this(ticket.id, ticket.eventId, ticket.userId, ticket.sitingNumber, ticket.category);
    }
}
