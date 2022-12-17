package net.anna.jwtappawss3.service;

import net.anna.jwtappawss3.model.Role;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;


public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository,roleRepository,passwordEncoder);
    }

    @Test
    public void save() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        ReflectionTestUtils.setField(userService, "roleRepository", roleRepository);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
        User user = new User();
        user.setUsername("Anna");
        Role role = new Role();
        role.setName("ROLE_USER");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(role);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(" ");
        assertEquals(user, userService.register(user));
    }

    @Test
    public void getById() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        User user = new User();
        user.setUsername("Anna");
        user.setId(35l);
        Mockito.when(userRepository.getById(Mockito.anyLong())).thenReturn(user);
        assertEquals(user.toString(), userService.findById(35l).toString());
    }

    @Test
    public void getAll(){
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUsername("Anna");
        users.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        assertEquals(users.toString(), userService.getAll().toString());
    }

    @Test
    public void deleteById() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        doNothing().when(userRepository).deleteById(15l);
        userService.delete(15l);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(Mockito.any());
    }

}
