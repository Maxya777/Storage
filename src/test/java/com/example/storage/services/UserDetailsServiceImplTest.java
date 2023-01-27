package com.example.storage.services;

import com.example.storage.entities.User;
import com.example.storage.model.SecurityUser;
import com.example.storage.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final User user = new User(USERNAME, PASSWORD, null);
    private final SecurityUser securityUser = new SecurityUser(user);

    @Test
    void loadUserByUsernameTest() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);
        assertEquals(securityUser.getPassword(), userDetails.getPassword());
    }
}
