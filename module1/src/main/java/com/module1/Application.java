package com.module1;


import com.module1.facade.BookingFacade;
import com.module1.model.Event;
import com.module1.model.Ticket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        BookingFacade bean = applicationContext.getBean(BookingFacade.class);
        Event eventById = bean.getEventById(1);
        bean.bookTicket(1L, 1L, 1, Ticket.Category.COMMON);
        bean.getBookedTickets(eventById, 0, 0);
    }

}
