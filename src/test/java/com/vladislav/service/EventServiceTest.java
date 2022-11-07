package com.vladislav.service;

import com.vladislav.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EventServiceTest {
    EventService eventService = new EventService();
    Event eventById;

    @BeforeEach
    void setUp() {
        eventById = eventService.getEventById(1);
    }

    @Test
    public void shouldReturnEventById() {
        assertThat(eventById.getId()).isEqualTo(1);
        assertThat(eventById.getName()).isEqualTo("loaded test.txt");
    }
}
