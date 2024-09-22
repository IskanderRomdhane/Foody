package com.Foody.Foody.Role;


import com.Foody.Foody.User.User;

import java.time.LocalDateTime;
import java.util.List;

public record nRole(
        String name,
        List<User> user ,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}