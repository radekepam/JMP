package com.module1.service;

import com.module1.dao.TicketRepository;
import com.module1.model.Event;
import com.module1.model.Ticket;
import com.module1.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class TicketServiceImplTest {
    private static final LocalDateTime EVENT_DATE_TIME = LocalDateTime.of(2023, Month.JANUARY, 10, 10, 0, 0);
    private TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository = Mockito.mock(TicketRepository.class);
    }

    @Test
    void bookTicketShouldThrowIllegalArgumentExceptionWhenRequiredTicketNotExists() {

        //given
        Ticket ticket = createTicket();
        when(ticketRepository.getTicketForEvent(5L, 1, Ticket.Category.COMMON)).thenReturn(Optional.empty());
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        assertThatThrownBy(() -> ticketService.bookTicket(1, 5L, 1, Ticket.Category.COMMON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ticket no: 1 not exists for event with id: 5 and for category: " + Ticket.Category.COMMON);
    }

    @Test
    void bookTicketShouldThrowIllegalStateExceptionWhenRequiredPlaceIsAlreadyBooked() {

        //given
        Ticket ticket = createTicket(1L);
        when(ticketRepository.getTicketForEvent(1L, 1, Ticket.Category.COMMON)).thenReturn(Optional.of(ticket));
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        assertThatThrownBy(() -> ticketService.bookTicket(1, 1L, 1, Ticket.Category.COMMON))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This place " + 1 + " has already been booked");
    }

    @Test
    void bookTicketShouldSetUserIdForTicketWhenTicketIsBookedSuccessfully() {

        //given
        Ticket ticket = createTicket();
        when(ticketRepository.getTicketForEvent(1L, 1, Ticket.Category.COMMON)).thenReturn(Optional.of(ticket));
        when(ticketRepository.update(ticket)).thenReturn(ticket);
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        Ticket result = ticketService.bookTicket(1L, 1L, 1, Ticket.Category.COMMON);

        //then
        assertThat(result.getUserId()).isEqualTo(1L);
    }

    @Test
    void getBookedTicketsShouldReturnBookedTicketsForUser() {

        //given
        Ticket ticket = createTicket(1L);
        when(ticketRepository.getBookedTicketsForUser(1L)).thenReturn(List.of(ticket));
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        List<Ticket> result = ticketService.getBookedTickets(createUser(), 0, 0);

        //then
        assertThat(result).allMatch(t -> t.getUserId().equals(1L));
    }

    @Test
    void getBookedTicketsShouldReturnEmptyListWhenBookedTicketsForUserNotExist() {

        //given
        when(ticketRepository.getBookedTicketsForUser(1L)).thenReturn(List.of());
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        List<Ticket> result = ticketService.getBookedTickets(createUser(), 0, 0);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void getBookedTicketsShouldReturnBookedTicketsForEvent() {

        //given
        Ticket ticket = createTicket(1L);
        Event event = createEvent();
        when(ticketRepository.getBookedTicketsForEvent(1L)).thenReturn(List.of(ticket));
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        List<Ticket> result = ticketService.getBookedTickets(event, 0, 0);

        //then
        assertThat(result).allMatch(t -> t.getEventId().equals(1L));
    }

    @Test
    void getBookedTicketsShouldReturnEmptyListTicketsForEvent() {

        //given
        Ticket ticket = createTicket(1L);
        Event event = createEvent();
        when(ticketRepository.getBookedTicketsForEvent(1L)).thenReturn(List.of(ticket));
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        List<Ticket> result = ticketService.getBookedTickets(event, 0, 0);

        //then
        assertThat(result).allMatch(t -> t.getEventId().equals(1L));
    }

    @Test
    void cancelTicketShouldReturnTrueWhenTicketIsCancelledSuccessfully() {

        //given
        Ticket ticket = createTicket(1L);
        when(ticketRepository.cancelTicket(1L)).thenReturn(true);
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        boolean result = ticketService.cancelTicket(1L);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void cancelTicketShouldReturnFalseWhenTicketIsNotCancelledSuccessfully() {

        //given
        Ticket ticket = createTicket(1L);
        when(ticketRepository.cancelTicket(1L)).thenReturn(false);
        TicketService ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "ticketRepository", ticketRepository);

        //when
        boolean result = ticketService.cancelTicket(1L);

        //then
        assertThat(result).isFalse();
    }

    private Ticket createTicket() {
        return this.createTicket(null);
    }

    private Ticket createTicket(Long userId) {
        return Ticket.builder()
                .id(1L)
                .sitingNumber(1)
                .eventId(1L)
                .userId(userId)
                .category(Ticket.Category.COMMON)
                .build();
    }

    private User createUser() {
        return new User(1L, "Name", "mail");
    }

    private Event createEvent() {
        return new Event(1L, "title", EVENT_DATE_TIME, 100);
    }
}