package com.vladislav.service;

import com.vladislav.model.Event;
import com.vladislav.repository.EventRepository;
import com.vladislav.repository.hibernate.HiberEventRepository;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository = new HiberEventRepository();

    public Event getEventById(int id) {
        return eventRepository.getById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event addNewEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public boolean deleteEventById(int id) {
        return eventRepository.deleteById(id);
    }
}
