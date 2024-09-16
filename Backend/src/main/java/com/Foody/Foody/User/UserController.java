package com.Foody.Foody.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("create-user")
    public ResponseEntity<String> createUser (
            @RequestBody rUser rUser
    ){
        return userService.createUser(rUser);
    }
    @PutMapping("change-details/{user_id}")
    public ResponseEntity<String> changeDetails(
            @PathVariable("user_id") Integer user_id,
            @RequestBody rUser user_Details
    ) {
        return userService.changeDetails(user_id, user_Details);
    }
    @GetMapping("/get-biometrics/{userId}")
    public ResponseEntity<?> getUserWithBiometrics(@PathVariable Integer userId) {
        return userService.getUserWithBiometrics(userId);
    }

    @GetMapping("/get-body-type/{userId}")
    public ResponseEntity<?> getUserBodyType(@PathVariable Integer userId) {
        return userService.getUserBodyType(userId);
    }

}
