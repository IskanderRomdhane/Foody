package com.Foody.Foody.User;

import com.Foody.Foody.Biometrics.Biometrics;
import com.Foody.Foody.Biometrics.BiometricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BiometricsRepository biometricsRepository;
    public ResponseEntity<String> createUser(rUser rUser) {
        try {

            User createdUser = User.builder()
                    .firstname(rUser.firstname())
                    .lastname(rUser.lastname())
                    .build();
            userRepository.save(createdUser);

            Biometrics userBiometrics = Biometrics.builder()
                    .age(rUser.age())
                    .height(rUser.height())
                    .weight(rUser.weight())
                    .ibm(rUser.weight() * 10000 / (rUser.height() * rUser.height()))
                    .user_id(createdUser.getId())
                    .cDate(LocalDate.now())
                    .build();
            biometricsRepository.save(userBiometrics);

            createdUser.setBiometrics(List.of(userBiometrics));
            userRepository.save(createdUser);

            return ResponseEntity.ok("User Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }


    public ResponseEntity<String> changeDetails(Integer userId, rUser userDetails) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User foundUser = userOptional.get();

                foundUser.setFirstname(userDetails.firstname());
                foundUser.setLastname(userDetails.lastname());

                Biometrics newBiometrics = Biometrics.builder()
                        .age(userDetails.age())
                        .height(userDetails.height())
                        .weight(userDetails.weight())
                        .ibm(userDetails.weight() * 10000 / (userDetails.height() * userDetails.height()))
                        .user_id(foundUser.getId())
                        .cDate(LocalDate.now())
                        .build();

                List<Biometrics> biometricsList = foundUser.getBiometrics();
                if (biometricsList == null) {
                    biometricsList = new ArrayList<>();
                }
                biometricsList.add(newBiometrics);

                foundUser.setBiometrics(biometricsList);

                biometricsRepository.save(newBiometrics);
                userRepository.save(foundUser);
                return ResponseEntity.ok("Saved User Successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Editing user: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getUserWithBiometrics(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<?> getUserBodyType(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Biometrics> biometricsList = user.getBiometrics();
            Biometrics currentBiometrics = biometricsList.get(biometricsList.size() - 1);

            return ResponseEntity.ok(currentBiometrics.getBodyType());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
