package com.module1.service;



import com.module1.model.Event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventService {

    Event getEventById(long eventId);

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param title model.Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     * @param day Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum);

    /**
     * Creates new event. model.Event id should be auto-generated.
     * @param event model.Event data.
     * @return Created model.Event object.
     */
    Event createEvent(Event event);

    /**
     * Updates event using given data.
     * @param event model.Event data for update. Should have id set.
     * @return Updated model.Event object.
     */
    Event updateEvent(Event event);

    /**
     * Deletes event by its id.
     * @param eventId model.Event id.
     * @return Flag that shows whether event has been deleted.
     */
    boolean deleteEvent(long eventId);

}
