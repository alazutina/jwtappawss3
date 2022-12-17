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

class UserRestControllerV1Test {

    @Mock
    UserService userService;

    @Mock
    FileService fileService;

    @Mock
    EventService eventService;

    UserRestControllerV1 userRestControllerV1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userRestControllerV1 = new UserRestControllerV1(userService,fileService,eventService);
    }

    @Test
    void getUserById() {
        ReflectionTestUtils.setField(userRestControllerV1, "userService", userService);
        User  user = new User();
        user.setUsername("Anna");
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
        UserDto result = UserDto.fromUser(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(result, HttpStatus.OK);
        assertEquals(response, userRestControllerV1.getUserById(1l));
     }

    @Test
    void getAllUsers() {
        ReflectionTestUtils.setField(userRestControllerV1, "userService", userService);
        List<User> arrayList = new ArrayList<>();
        User  user = new User();
        user.setUsername("Anna");
        arrayList.add(user);
        Mockito.when(userService.getAll()).thenReturn(arrayList);
        List<UserDto> dtos=new ArrayList<>();
        for( User u : arrayList){
            dtos.add(UserDto.fromUser(u));
        }
        ResponseEntity<List<UserDto>> response = new ResponseEntity<>(dtos, HttpStatus.OK);
        assertEquals(response, userRestControllerV1.getAllUsers());
    }

    @Test
    void userGetAllFiles() {
        ReflectionTestUtils.setField(userRestControllerV1, "fileService", fileService);
        List<File> arrayList = new ArrayList<>();
        File file = new File();
        file.setPath("11.txt");
        arrayList.add(file);
        Mockito.when(fileService.getAll()).thenReturn(arrayList);
        List<FileDto> fileDtos=new ArrayList<>();
        for( File f : arrayList){
            fileDtos.add(FileDto.fromFile(f));
        }
        ResponseEntity<List<FileDto>> response = new ResponseEntity<>(fileDtos, HttpStatus.OK);
        assertEquals(response, userRestControllerV1.userGetAllFiles());
    }

    @Test
    void userGetFileById() {
        ReflectionTestUtils.setField(userRestControllerV1, "fileService", fileService);
        File file = new File();
        file.setPath("11.txt");
        Mockito.when(fileService.findById(Mockito.anyLong())).thenReturn(file);
        FileDto result = FileDto.fromFile(file);
        ResponseEntity<FileDto> response = new ResponseEntity<>(result, HttpStatus.OK);
        assertEquals(response, userRestControllerV1.userGetFileById(1l));
            }

    @Test
    void getAllEvents() {
        ReflectionTestUtils.setField(userRestControllerV1, "eventService", eventService);
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
        assertEquals(response, userRestControllerV1.getAllEvents());
    }

    @Test
    void getEventById() {
        ReflectionTestUtils.setField(userRestControllerV1, "eventService", eventService);
        Event event = new Event();
        event.setAction("Uploaded");
        Mockito.when(eventService.findById(Mockito.anyLong())).thenReturn(event);
        EventDto eventDto  = EventDto.fromEvent(event);
        ResponseEntity<EventDto> response = new ResponseEntity<>(eventDto, HttpStatus.OK);
        assertEquals(response, userRestControllerV1.getEventById(1l));
    }
}