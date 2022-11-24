package net.anna.jwtappawss3.service.impl;

import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.repository.EventRepository;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private  final UserRepository userRepository;
    private  final RoleRepository roleRepository;
    private  final BCryptPasswordEncoder passwordEncoder;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventRepository = eventRepository;
    }

    @Override
    public Event register(Event event) {
        Event event1 = eventRepository.save(event);
        log.info("In saving - event {} successfully save ", event1);
        return event1;
    }

    @Override
    public List<Event> getAll() {
        List<Event> result = eventRepository.findAll();
        log.info("in getAll - {} events found", result.size());
        return result;
    }

    @Override
    public Event findById(Long id) {
        Event result = eventRepository.findById(id).orElse(null);
        if(result==null){
            log.warn("In findById - no event found by id {}", id);
            return null;
        }
        log.warn("In findById - event {} found by id {}", result,id);
        return result;
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
        log.info("in delete - event with id {} deleted", id);
    }
}
