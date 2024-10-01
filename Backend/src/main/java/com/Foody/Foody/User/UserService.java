package com.Foody.Foody.User;

import com.Foody.Foody.Biometrics.Biometrics;
import com.Foody.Foody.Biometrics.BiometricsRepository;
import com.Foody.Foody.Food.FoodRepository;
import com.Foody.Foody.Role.RoleRepository;
import com.Foody.Foody.Security.JwtService;
import com.Foody.Foody.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BiometricsRepository biometricsRepository;
    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    public ResponseEntity<?> createUser(RegistrationRequest request) {
        try {
            // Build the new User entity
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountLocked(false)
                    .enabled(true)
                    .build();

            // Create and update the user's biometrics
            Biometrics userBiometrics = createOrUpdateBiometrics(request, user);
            user.setBiometrics(List.of(userBiometrics));

            // Save the user again with the updated biometrics
            userRepository.save(user);

            return ResponseEntity.ok(user);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error saving user due to data integrity violation: " + e.getMessage());
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
                RegistrationRequest request = mapToRegistrationRequest(userDetails);
                Biometrics newBiometrics = createOrUpdateBiometrics(request , foundUser);
                List<Biometrics> biometricsList = foundUser.getBiometrics();
                if (biometricsList == null) {
                    biometricsList = new ArrayList<>();
                }
                biometricsList.add(newBiometrics);

                foundUser.setBiometrics(biometricsList);
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

    public RegistrationRequest mapToRegistrationRequest(rUser userDetails) {
        return RegistrationRequest.builder()
                .age(userDetails.age())
                .height(userDetails.height())
                .weight(userDetails.weight())
                .gender(userDetails.gender())
                .activityLevel(userDetails.activityLevel())
                .goal(userDetails.goal())
                .build();
    }


    private Biometrics createOrUpdateBiometrics(RegistrationRequest userDetails, User user) {
        Biometrics newBiometrics = Biometrics.builder()
                .age(userDetails.getAge())
                .height(userDetails.getHeight())
                .weight(userDetails.getWeight())
                .ibm(userDetails.getWeight() * 10000 / (userDetails.getHeight() * userDetails.getHeight()))
                .user_id(user.getId())
                .cDate(LocalDate.now())
                .gender(userDetails.getGender())
                .activityLevel(userDetails.getActivityLevel())
                .goal(userDetails.getGoal())
                .build();

        float bmr = calculateBMR(newBiometrics);
        float tdee = calculateTDEE(bmr, userDetails.getActivityLevel());

        Map<String, String> goals = calculateCaloricGoals(tdee);

        String caloriesIntakeString = goals.get(userDetails.getGoal());
        float caloriesIntake = Float.parseFloat(caloriesIntakeString);

        float proteinIntake = caloriesIntake * 0.15F / 4; // 15% of calories from protein
        float carbsIntake = caloriesIntake * 0.55F / 4;   // 55% of calories from carbs
        float fatsIntake = caloriesIntake * 0.30F / 9;    // 30% of calories from fats

        newBiometrics.setCaloriesIntake(caloriesIntake);
        newBiometrics.setProteinIntake(proteinIntake);
        newBiometrics.setCarbsIntake(carbsIntake);
        newBiometrics.setFatsIntake(fatsIntake);

        biometricsRepository.save(newBiometrics);
        return  newBiometrics;
    }

    public ResponseEntity<?> getUserWithBiometrics(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user.getBiometrics());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<?> getUserBodyType(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Biometrics> biometricsList = user.getBiometrics();
            Biometrics currentBiometrics = biometricsList.get(biometricsList.size() - 1);

            return ResponseEntity.ok(currentBiometrics.getBodyType());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }



    public ResponseEntity<Map<String, String>> calculateCalorieNeeds(String email, String activityLevel) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            try {
                User foundUser = userOptional.get();
                List<Biometrics> biometricsList = foundUser.getBiometrics();

                Biometrics currentBiometrics = biometricsList.get(biometricsList.size() - 1);
                float bmr = calculateBMR(currentBiometrics);
                float tdee = calculateTDEE(bmr, activityLevel);

                Map<String, String> caloricGoals = calculateCaloricGoals(tdee);

                return ResponseEntity.ok(caloricGoals);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", "Error calculating calorie needs: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found"));
        }
    }

    private float calculateBMR(Biometrics biometrics) {
        float weight = biometrics.getWeight();
        float height = biometrics.getHeight();
        int age = biometrics.getAge();
        String gender = biometrics.getGender();

        if (gender.equalsIgnoreCase("male")) {
            return (10 * weight) + (6.25f * height) - (5 * age) + 5;
        } else if (gender.equalsIgnoreCase("female")) {
            return (10 * weight) + (6.25f * height) - (5 * age) - 161;
        } else {
            throw new IllegalArgumentException("Gender must be either 'male' or 'female'");
        }
    }

    private float calculateTDEE(float bmr, String activityLevel) {
        return switch (activityLevel.toLowerCase()) {
            case "sedentary" -> bmr * 1.2f;
            case "lightly active" -> bmr * 1.375f;
            case "moderately active" -> bmr * 1.55f;
            case "very active" -> bmr * 1.725f;
            case "super active" -> bmr * 1.9f;
            default -> throw new IllegalArgumentException("Invalid activity level provided");
        };
    }

    private Map<String, String> calculateCaloricGoals(float tdee) {
        Map<String, String> caloricGoals = new HashMap<>();
        caloricGoals.put("maintainWeight", String.format("%.2f", tdee));
        caloricGoals.put("gradualGain", String.format("%.2f", tdee + 375));
        caloricGoals.put("extremeGain", String.format("%.2f", tdee + 750));
        caloricGoals.put("gradualLoss", String.format("%.2f", tdee - 375));
        caloricGoals.put("extremeLoss", String.format("%.2f", tdee - 750));
        return caloricGoals;
    }

    public ResponseEntity<?> getUserWeeklyWeightProgression(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Biometrics> biometricsList = user.getBiometrics();
            Map<String, Float> progression = new HashMap<>();
            Integer weekN = 0;

            if (!biometricsList.isEmpty()) {
                Biometrics previousBiometrics = biometricsList.get(0);
                weekN++;
                progression.put("Week" + weekN, previousBiometrics.getWeight());

                for (int i = 1; i < biometricsList.size(); i++) {
                    Biometrics currentBiometrics = biometricsList.get(i);

                    if (previousBiometrics.getCDate().plusWeeks(1).isBefore(currentBiometrics.getCDate())
                            || previousBiometrics.getCDate().plusWeeks(1).isEqual(currentBiometrics.getCDate())) {
                        weekN++;
                        progression.put("Week" + weekN, currentBiometrics.getWeight());
                        previousBiometrics = currentBiometrics;
                    }
                }

            }
            return ResponseEntity.ok(progression);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    public ResponseEntity<?> isAchievingGoal(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Biometrics> biometricsList = user.getBiometrics();
            Boolean goal = false;
            Biometrics first = biometricsList.get(0);
            Biometrics last = biometricsList.get(biometricsList.size() - 1);
            switch (first.getGoal()) {
                case "maintainWeight" -> {
                    goal = (last.getWeight() - first.getWeight()) == 0;
                }
                case "gradualGain" -> {
                    // For gradual gain, we expect a small weight increase like 1-2 kg
                    goal = (last.getWeight() - first.getWeight()) > 0 && (last.getWeight() - first.getWeight()) <= 2;
                }
                case "extremeGain" -> {
                    // For extreme gain, we expect weight increase over 2 kg
                    goal = (last.getWeight() - first.getWeight()) > 2;
                }
                case "gradualLoss" -> {
                    // For gradual loss, we expect weight decrease like 1-2 kg
                    goal = (first.getWeight() - last.getWeight()) > 0 && (first.getWeight() - last.getWeight()) <= 2;
                }
                case "extremeLoss" -> {
                    // For extreme loss, we expect a significant weight decrease over 2 kg
                    goal = (first.getWeight() - last.getWeight()) > 2;
                }
                default -> throw new IllegalStateException("Unexpected goal: " + first.getGoal());
            }
            return ResponseEntity.ok(goal);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User Not found");
        }
    }


}
