package net.anna.jwtappawss3.service;

import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.repository.EventRepository;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

public class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    EventService eventService;
    FileService fileService;
    UserService userService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        eventService = new EventServiceImpl( userRepository,  roleRepository, passwordEncoder, eventRepository);
    }

    @Test
    public void register() {
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
        Event event = new Event();
        File file = new File();
        event.setFile_id(file.getId());
        User user = new User();
        event.setUser_id(user.getId());
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);
        assertEquals(event.toString(), eventService.register(event).toString());
    }

    @Test
    public void getAll(){
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        File file = new File();
        event.setFile_id(file.getId());
        User user = new User();
        user.setUsername("Anna");
        event.setUser_id(user.getId());
        event.setId(20L);
        events.add(event);
        Mockito.when(eventRepository.findAll()).thenReturn(events);
        assertEquals(events, eventService.getAll());
    }
    @Test
    public void findById() {
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
        Event event = new Event();
        File file = new File();
        event.setFile_id(file.getId());
        User user = new User();
        user.setUsername("Anna");
        event.setUser_id(user.getId());
        event.setId(20L);
        Mockito.when(eventRepository.getById(Mockito.anyLong())).thenReturn(event);
        assertEquals(event.toString(), eventService.findById(20l).toString());
    }

    @Test
    public void deleteById() {
        ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
        doNothing().when(eventRepository).deleteById(15l);
        eventService.delete(15l);
        Mockito.verify(eventRepository,Mockito.times(1)).deleteById(Mockito.any());
    }

}
