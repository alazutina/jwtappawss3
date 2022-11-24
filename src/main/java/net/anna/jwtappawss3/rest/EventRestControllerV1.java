package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/events/")
public class EventRestControllerV1 {

        private final EventService eventService;

        @Autowired
        public EventRestControllerV1(EventService eventService) {
        this.eventService = eventService;
        }

        @GetMapping(value = "")
        public ResponseEntity<List<EventDto>> getAllEvents() {
            List<Event> arrayList = eventService.getAll();
            if(arrayList==null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<EventDto> eventDtos=new ArrayList<>();
            for( Event event : arrayList){
                eventDtos.add(EventDto.fromEvent(event));
            }
            return new ResponseEntity<>(eventDtos,HttpStatus.OK);
        }

        @GetMapping(value = "{id}")
        public ResponseEntity<EventDto> getEventById(@PathVariable(name = "id") Long id){
            Event event = eventService.findById(id);
            if(event==null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            EventDto eventDto  = EventDto.fromEvent(event);
            return new ResponseEntity<>(eventDto,HttpStatus.OK);
        }

        @DeleteMapping(value = "{id}")
        public ResponseEntity<FileDto> deleteEventById(@PathVariable(name = "id") Long id){
            eventService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        @PostMapping("/save")
        public ResponseEntity<EventDto> saveEvent(@RequestBody Event event) {
            Event event1 = eventService.register(event);
            EventDto eventDto = EventDto.fromEvent(event1);
            return new ResponseEntity<>(eventDto,HttpStatus.OK);
        }


}
