package com.Foody.Foody.Token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tokens/")
public class TokenController {
    private final TokenService tokenService;
    @PutMapping("refresh-token")
    public Token refreshToken (
            @RequestBody Token token
    ){
        return tokenService.refreshToken(token);
    }
}
