package com.vireak.bidding.service;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.enums.Role;
import com.vireak.bidding.model.UserRequest;
import com.vireak.bidding.model.UserResponse;
import com.vireak.bidding.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserRequest userRequest;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userRequest = UserRequest.builder()
                .email("test@mail.com")
                .role(Role.ADMIN)
                .lastName("lastname")
                .firstName("firstname")
                .password("password")
                .build();
    }

    @Test
    void registerUser() {
        // TODO: Validate the user fields from UI
        User userExp = User.builder()
                .email(userRequest.getEmail())
                .build();
        when(userRepository.save(any(User.class))).thenReturn(userExp);
        User user = userService.registerUser(userRequest);
        assertEquals(userExp.getEmail(), user.getEmail());
    }

    @Test
    void findByEmail() {
        User userExp = User.builder()
                .email(userRequest.getEmail())
                .build();
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.ofNullable(userExp));
        User user = userService.findByEmail(userRequest.getEmail());
        assert user != null && userExp != null;
        assertEquals(userExp.getEmail(), user.getEmail());
    }

    @Test
    void findAll() {
        User userExp = User.builder()
                .email(userRequest.getEmail())
                .build();
        when(userRepository.findAll()).thenReturn(List.of(userExp));
        for (UserResponse userResponse : userService.findAll()) {
            assertEquals(
                    userResponse.getEmail(),
                    userExp.getEmail()
            );
        }
    }

}