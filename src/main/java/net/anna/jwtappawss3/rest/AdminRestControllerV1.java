package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.AdminUserDto;
import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.dto.UserDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Status;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.service.EventService;
import net.anna.jwtappawss3.service.FileService;
import net.anna.jwtappawss3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final FileService fileService;
    private final EventService eventService;

    @Autowired
    public AdminRestControllerV1(UserService userService, FileService fileService, EventService eventService) {
        this.userService = userService;
        this.fileService = fileService;
        this.eventService = eventService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "users/")
    public ResponseEntity<List<UserDto>> adminGetAllUsers(){
        List<User> arrayList = userService.getAll();
        if(arrayList==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> userDtos=new ArrayList<>();
        for( User u: arrayList){
            userDtos.add(UserDto.fromUser(u));
        }
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @DeleteMapping(value = "{users}/{id}")
    public ResponseEntity<UserDto> AdminDeleteUserById(@PathVariable(name = "id") Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> adminSaveUser(@RequestBody User user) {
        User user1 = userService.register(user);
        UserDto result = UserDto.fromUser(user1);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @GetMapping(value = "files/")
    public ResponseEntity<List<FileDto>> getAllFiles() {
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
    public ResponseEntity<FileDto> adminGetFileById(@PathVariable(name = "id") Long id){
        File file = fileService.findById(id);
        if(file==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        FileDto result = FileDto.fromFile(file);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping(value = "{files}/{id}")
    public ResponseEntity<FileDto> AdminDeleteFileById(@PathVariable(name = "id") Long id){
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("save/file")
    public ResponseEntity<FileDto> adminSaveFile(@RequestBody File object) {
        System.out.println(object+"!");
        object.setStatus(Status.ACTIVE);
        File file1 =   fileService.register(object);
        FileDto result = FileDto.fromFile(file1);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<FileDto> adminUpdateFile(@RequestBody File object) {
        File file1 =   fileService.update(object);
        FileDto result = FileDto.fromFile(file1);
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

    @DeleteMapping(value = "{events}/{id}")
    public ResponseEntity<FileDto> deleteEventById(@PathVariable(name = "id") Long id){
        eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("save/event")
    public ResponseEntity<EventDto> saveEvent(@RequestBody Event event) {
        Event event1 = eventService.register(event);
        EventDto eventDto = EventDto.fromEvent(event1);
        return new ResponseEntity<>(eventDto,HttpStatus.OK);
    }


}
