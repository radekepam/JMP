package com.example.module3.eventServiceDto.dto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAllByTitle(String title);
}
