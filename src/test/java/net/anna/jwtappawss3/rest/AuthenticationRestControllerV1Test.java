package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.AuthenticationRequestDto;
import net.anna.jwtappawss3.model.Role;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.security.jwt.JwtTokenProvider;
import net.anna.jwtappawss3.service.UserService;
import net.anna.jwtappawss3.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class AuthenticationRestControllerV1Test {
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    UserService userService;

    AuthenticationRestControllerV1 authenticationRestControllerV1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationRestControllerV1 = new AuthenticationRestControllerV1(authenticationManager,jwtTokenProvider,userService);
    }

    @Test
    void login() {
        ReflectionTestUtils.setField(authenticationRestControllerV1, "authenticationManager", authenticationManager);
        ReflectionTestUtils.setField(authenticationRestControllerV1, "jwtTokenProvider", jwtTokenProvider);
        ReflectionTestUtils.setField(authenticationRestControllerV1, "userService", userService);
        Authentication authentication = new UsernamePasswordAuthenticationToken("Anna","1");
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        User  user = new User();
        user.setUsername("Anna");
        Role role = new Role();
        role.setName("ROLE_USER");
        List<Role> list = new ArrayList<>();
        list.add(role);
        user.setRoles(list);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(jwtTokenProvider.createToken(Mockito.anyString(),Mockito.anyList())).thenReturn("12345");
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setUsername("Anna");
        requestDto.setPassword("1");
        Map<Object,Object> response = new HashMap<>();
        response.put("username", "Anna");
        response.put("token", "12345");
        ResponseEntity responseEntity= ResponseEntity.ok(response);
        assertEquals(responseEntity, authenticationRestControllerV1.login(requestDto));
    }
}