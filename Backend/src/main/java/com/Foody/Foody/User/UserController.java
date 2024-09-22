package com.Foody.Foody.User;

import com.Foody.Foody.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PutMapping("change-details/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changeDetails(
            @PathVariable("user_id") Integer user_id,
            @RequestBody rUser user_Details
    ) {
        return userService.changeDetails(user_id, user_Details);
    }


    @GetMapping("/get-biometrics")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserWithBiometrics() {
        String email = jwtService.getCurrentUserEmail();
        return userService.getUserWithBiometrics(email);
    }


    @GetMapping("/get-body-type")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserBodyType() {
        String email = jwtService.getCurrentUserEmail();
        return userService.getUserBodyType(email);
    }

    @PostMapping("/calorie-needs/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> calculateCalorieNeeds(@RequestParam String activityLevel) {
        String email = jwtService.getCurrentUserEmail();
        return userService.calculateCalorieNeeds(email, activityLevel);
    }

    @GetMapping("/get-weekly-weight-progression")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserWeeklyWeightProgression() {
        String email = jwtService.getCurrentUserEmail();
        return userService.getUserWeeklyWeightProgression(email);
    }

    @GetMapping("/get-goal")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> isAchievingGoal() {
        String email = jwtService.getCurrentUserEmail();
        return userService.isAchievingGoal(email);
    }

}
