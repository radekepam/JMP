package com.module1.service;

import com.module1.dao.EventRepository;
import com.module1.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EventServiceImplTest {
    private static final LocalDateTime EVENT_DATE_TIME = LocalDateTime.of(2023, Month.JANUARY, 10, 10, 0, 0);
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = Mockito.mock(EventRepository.class);
    }

    @Test
    void getEventByIdShouldReturnEvent() {
        //given
        Event event = createEvent();
        when(eventRepository.getEventById(1L)).thenReturn(Optional.of(event));
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        Event result = eventService.getEventById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo(event.getTitle());
        assertThat(result.getSittings()).isEqualTo(event.getSittings());
        assertThat(result.getStartDate()).isEqualTo(event.getStartDate());
    }

    @Test
    void getEventByIdShouldThrowNoSuchElementExceptionWhenEventWithGivenIdNotExist() {
        //given
        when(eventRepository.getEventById(1L)).thenReturn(Optional.empty());
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when & then
        assertThatThrownBy(() -> eventService.getEventById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Event with given Id not exist");

    }

    @Test
    void getEventByTitleShouldReturnEventsContainsGivenTitle() {
        //given
        Event event = createEvent();
        when(eventRepository.getEventsByTitle("title")).thenReturn(List.of(event));
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        List<Event> result = eventService.getEventsByTitle("title", 0, 0);

        //then
        assertThat(result).hasSize(1);
    }

    @Test
    void getEventByTitleShouldReturnEmptyListWhenNoneEventContainsGivenTitle() {
        //given
        Event event = createEvent();
        when(eventRepository.getEventsByTitle("title")).thenReturn(List.of());
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        List<Event> result = eventService.getEventsByTitle("title", 0, 0);

        //then
        assertThat(result).hasSize(0);
    }

    @Test
    void getEventsForDayShouldReturnEventsStartedAtGivenDay() {
        //given
        Event event = createEvent();
        when(eventRepository.getEventsForDay(EVENT_DATE_TIME.toLocalDate())).thenReturn(List.of(event));
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        List<Event> result = eventService.getEventsForDay(EVENT_DATE_TIME.toLocalDate(), 0, 0);

        //then
        assertThat(result).hasSize(1);
    }

    @Test
    void getEventsForDayShouldReturnEmptyListWhenNoneEventIsStartedAtGivenDay() {
        //given
        Event event = createEvent();
        when(eventRepository.getEventsForDay(EVENT_DATE_TIME.toLocalDate())).thenReturn(List.of());
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        List<Event> result = eventService.getEventsForDay(EVENT_DATE_TIME.toLocalDate(), 0, 0);

        //then
        assertThat(result).hasSize(0);
    }

    @Test
    void createEventShouldReturnCreatedEvent() {
        //given
        Event event = createEvent();
        when(eventRepository.createEvent(any())).thenReturn(event);
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        eventService.createEvent(event);

        //then
        verify(eventRepository, times(1)).createEvent(event);
    }

    @Test
    void updateEventShouldReturnUpdatedEvent() {
        //given
        Event event = createEvent();
        when(eventRepository.createEvent(any())).thenReturn(event);
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        eventService.createEvent(event);

        //then
        verify(eventRepository, times(1)).createEvent(event);
    }

    @Test
    void deleteEventShouldReturnTrueWhenEventDeleted() {
        //given
        when(eventRepository.deleteById(anyLong())).thenReturn(true);
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        boolean result = eventService.deleteEvent(1);

        //then
        verify(eventRepository, times(1)).deleteById(1);
        assertThat(result).isTrue();
    }

    @Test
    void deleteEventShouldReturnFalseWhenRepositoryDidNotDeleteEvent() {
        //given
        when(eventRepository.deleteById(anyLong())).thenReturn(false);
        EventService eventService = new EventServiceImpl();
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);

        //when
        boolean result = eventService.deleteEvent(1);

        //then
        verify(eventRepository, times(1)).deleteById(1);
        assertThat(result).isFalse();
    }

    private Event createEvent() {
        return new Event(1L, "title", EVENT_DATE_TIME, 100);
    }

}