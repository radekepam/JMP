package dto;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Component
@AllArgsConstructor
public class StartData implements InitializingBean {

    public EventRepository eventRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        createEvents();
    }

    private void createEvents() {
        Event event1 = Event.builder()
                .eventType(EventType.SHOW)
                .title("Show event")
                .place("Theatre")
                .speaker("Speaker1")
                .dateTime(LocalDateTime.of(2023, Month.APRIL, 20, 20, 0))
                .build();
        Event event2 = Event.builder()
                .eventType(EventType.SPORT)
                .title("Sport event")
                .place("Stadium")
                .speaker("Speaker2")
                .dateTime(LocalDateTime.of(2024, Month.APRIL, 20, 20, 0))
                .build();
        Event event3 = Event.builder()
                .eventType(EventType.CONCERT)
                .title("Concert event")
                .place("Opera")
                .speaker("Speaker3")
                .dateTime(LocalDateTime.of(2025, Month.APRIL, 20, 20, 0))
                .build();
        eventRepository.saveAll(List.of(event1, event2, event3));
    }
}
