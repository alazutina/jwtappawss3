package net.anna.jwtappawss3.service;

import net.anna.jwtappawss3.model.Event;

import java.util.List;

public interface EventService {
    Event register(Event event);
    List<Event> getAll();
    Event findById(Long id);
    void delete(Long id);
}
