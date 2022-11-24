package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.dto.UserDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.service.EventService;
import net.anna.jwtappawss3.service.FileService;
import net.anna.jwtappawss3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {

    private final UserService userService;
    private final FileService fileService;
    private final EventService eventService;

    @Autowired
    public UserRestControllerV1(UserService userService, FileService fileService, EventService eventService) {
        this.userService = userService;
        this.fileService = fileService;
        this.eventService = eventService;
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> arrayList = userService.getAll();
        if(arrayList==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> dtos=new ArrayList<>();
        for( User u : arrayList){

            dtos.add(UserDto.fromUser(u));
        }
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping(value = "files/")
    public ResponseEntity<List<FileDto>> userGetAllFiles() {
        List<File> arrayList = fileService.getAll();
        if(arrayList==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<FileDto> fileDtos=new ArrayList<>();
        for( File f : arrayList){

            fileDtos.add(FileDto.fromFile(f));
        }
        return new ResponseEntity<>(fileDtos,HttpStatus.OK);
    }

    @GetMapping(value = "{files}/{id}")
    public ResponseEntity<FileDto> userGetFileById(@PathVariable(name = "id") Long id){
        File file = fileService.findById(id);
        if(file==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        FileDto result = FileDto.fromFile(file);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "events/")
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

    @GetMapping(value = "{events}/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable(name = "id") Long id){
        Event event = eventService.findById(id);
        if(event==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EventDto eventDto  = EventDto.fromEvent(event);
        return new ResponseEntity<>(eventDto,HttpStatus.OK);
    }

}
