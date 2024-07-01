package com.vireak.bidding.service;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.enums.Role;
import com.vireak.bidding.model.UserRequest;
import com.vireak.bidding.model.UserResponse;
import com.vireak.bidding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRequest userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        // TODO: Validate the user fields from UI
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(Role.USER)
                .build();
        if (Objects.nonNull(userRequest.getRole())) user.setRole(userRequest.getRole());
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(User::toResponse)
                .toList();
    }

    public User findById(Long createdBy) {
        return userRepository.findById(createdBy)
                .orElse(null);
    }
}
