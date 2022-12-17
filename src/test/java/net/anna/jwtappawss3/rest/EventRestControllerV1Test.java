package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class EventRestControllerV1Test {

    @Mock
    EventService eventService;

    EventRestControllerV1 eventRestControllerV1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        eventRestControllerV1 = new EventRestControllerV1(eventService);
    }

    @Test
    void getAllEvents() {
        ReflectionTestUtils.setField(eventRestControllerV1, "eventService", eventService);
        List<Event> arrayList = new ArrayList<>();
        Event event = new Event();
        event.setAction("Uploaded");
        arrayList.add(event);
        Mockito.when(eventService.getAll()).thenReturn(arrayList);
        List<EventDto> eventDtos=new ArrayList<>();
        for( Event e : arrayList){
            eventDtos.add(EventDto.fromEvent(e));
        }
        ResponseEntity<List<EventDto>> response = new ResponseEntity<>(eventDtos, HttpStatus.OK);
        assertEquals(response, eventRestControllerV1.getAllEvents());
    }

    @Test
    void getEventById() {
        ReflectionTestUtils.setField(eventRestControllerV1, "eventService", eventService);
        Event event = new Event();
        event.setAction("Uploaded");
        Mockito.when(eventService.findById(Mockito.anyLong())).thenReturn(event);
        EventDto eventDto  = EventDto.fromEvent(event);
        ResponseEntity<EventDto> response = new ResponseEntity<>(eventDto, HttpStatus.OK);
        assertEquals(response, eventRestControllerV1.getEventById(1l));
    }

    @Test
    void deleteEventById() {
        ReflectionTestUtils.setField(eventRestControllerV1, "eventService", eventService);
        doNothing().when(eventService).delete(15l);
        eventRestControllerV1.deleteEventById(15l);
        Mockito.verify(eventService,Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void saveEvent() {
        ReflectionTestUtils.setField(eventRestControllerV1, "eventService", eventService);
        Event event = new Event();
        event.setAction("Uploaded");
        Mockito.when(eventService.register(event)).thenReturn(event);
        EventDto eventDto  = EventDto.fromEvent(event);
        ResponseEntity<EventDto> response = new ResponseEntity<>(eventDto, HttpStatus.OK);
        assertEquals(response, eventRestControllerV1.saveEvent(event));
    }
}