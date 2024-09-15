package com.Foody.Foody.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
