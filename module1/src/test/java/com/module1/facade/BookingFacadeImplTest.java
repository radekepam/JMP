package com.module1.facade;

import com.module1.model.Event;
import com.module1.model.Ticket;
import com.module1.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookingFacadeImplTest {

    @Autowired
    private static ApplicationContext APPLICATION_CONTEXT;

    private BookingFacade bookingFacade;

    @BeforeAll
    public static void init() {
        APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");
    }

    @BeforeEach
    public void setUp() {
        bookingFacade = (BookingFacade) APPLICATION_CONTEXT.getBean("bookingFacade");

    }

    @Test
    public void createUserSuccess() {
        //given
        User user = new User("newUser", "newUserMail");

        //when
        User createdUser = bookingFacade.createUser(user);

        //then
        List<User> foundUsers = bookingFacade.getUsersByName("newUser", 0, 0);
        assertThat(createdUser.getId()).isEqualTo(foundUsers.get(0).getId());
        assertThat(createdUser.getName()).isEqualTo("newUser");
        assertThat(createdUser.getEmail()).isEqualTo("newUserMail");
    }

    @Test
    public void createEventSuccess() {
        //given
        Event event = new Event("newEventTitle", LocalDateTime.now(), 50);

        //when
        Event createdEvent = bookingFacade.createEvent(event);

        //then
        Event eventById = bookingFacade.getEventById(createdEvent.getId());
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(eventById, 0, 0);
        assertThat(createdEvent.getId()).isEqualTo(eventById.getId());
        assertThat(createdEvent.getSittings()).isEqualTo(eventById.getSittings());
        assertThat(createdEvent.getTitle()).isEqualTo(eventById.getTitle());
        assertThat(createdEvent.getStartDate()).isEqualTo(eventById.getStartDate());
        assertThat(bookedTickets.size()).isEqualTo(0);
    }

    @Test
    public void bookTicketSuccessfully() {
        //given
        Event event = bookingFacade.getEventById(1L);

        //when
        Ticket bookedTicket = bookingFacade.bookTicket(1L, event.getId(), 1, Ticket.Category.COMMON);
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(event, 0, 0);

        //then
        boolean isBooked = bookedTickets.stream()
                .anyMatch(t -> t.getSitingNumber() == 1);
        assertThat(isBooked).isTrue();
        assertThat(bookedTicket.getUserId()).isEqualTo(1L);
    }

    @Test
    public void cancelBookedTicket() {
        //given
        Event event = bookingFacade.getEventById(1L);
        Ticket bookedTicket = bookingFacade.bookTicket(1L, event.getId(), 1, Ticket.Category.COMMON);

        //when
        boolean isCancelled = bookingFacade.cancelTicket(bookedTicket.getId());

        //then
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(event, 0, 0);
        boolean ticketIsNotInBookingPlacesForEvent = bookedTickets.stream()
                .noneMatch(t -> t.getSitingNumber() == 1);
        assertThat(isCancelled).isTrue();
        assertThat(ticketIsNotInBookingPlacesForEvent).isTrue();
    }
}