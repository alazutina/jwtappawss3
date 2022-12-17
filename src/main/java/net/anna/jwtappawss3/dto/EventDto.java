package net.anna.jwtappawss3.dto;

import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private String action;
    private Long user_id;
    private Long file_id;

    public Event toEvent(){
        Event event = new Event();
        event.setAction(action);
        event.setFile_id(file_id);
        event.setUser_id(user_id);
        return event;
    }

    public  static  EventDto fromEvent(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setAction(event.getAction());
        eventDto.setFile_id(event.getFile_id());
        eventDto.setUser_id(event.getUser_id());
        return eventDto;
    }

}
