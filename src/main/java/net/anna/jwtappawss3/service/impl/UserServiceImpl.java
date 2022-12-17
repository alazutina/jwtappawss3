package net.anna.jwtappawss3.service.impl;

import net.anna.jwtappawss3.model.Role;
import net.anna.jwtappawss3.model.Status;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private  final UserRepository userRepository;
    private  final RoleRepository roleRepository;
    private  final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser=userRepository.save(user);

        log.info("In register - user {} successfully registered ", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("in getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("in findById - user {} found by username {} ", result,username);
    return  result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.getById(id);//findById(id).orElse(null);
        if(result==null){
            log.warn("In findById - no user found by id {}", id);
            return null;
        }
        log.warn("In findById - user {} found by id {}", result,id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("in delete - user with id {} deleted", id);

    }
}
