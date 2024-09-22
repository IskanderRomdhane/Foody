package com.Foody.Foody.Token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTime;

    private final TokenRepository tokenRepository;

    public Token refreshToken(Token token) {
        Optional<Token> oToken = tokenRepository.findByUserId(token.getUser().getId());
        if (oToken.isPresent()) {
            Token foundToken = oToken.get();
            LocalDateTime newExpiryTime = LocalDateTime.now().plusSeconds(refreshTime / 1000);
            foundToken.setExpiresAt(newExpiryTime);
            return tokenRepository.save(foundToken);
        }
        throw new RuntimeException("Token not found for user: " + token.getUser().getId());
    }
}


