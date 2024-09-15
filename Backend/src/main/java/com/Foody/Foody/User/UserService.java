package com.Foody.Foody.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public ResponseEntity<String> createUser(rUser rUser) {
        try {
            User createdUser = User.builder()
                    .firstname(rUser.firstname())
                    .lastname(rUser.lastname())
                    .height(rUser.height())
                    .weight(rUser.weight())
                    .build();
            userRepository.save(createdUser);
            return ResponseEntity.ok("User Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }
}
