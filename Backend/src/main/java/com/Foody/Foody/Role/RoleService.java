package com.Foody.Foody.Role;

import com.Foody.Foody.User.User;
import com.Foody.Foody.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Integer> addRole(nRole request) {
        Role role = Role.builder()
                .name(request.name())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .user(null)
                .build();
        roleRepository.save(role);
        return ResponseEntity.ok(role.getId());
    }

    public ResponseEntity<String> assignRole(Integer userId, nRole request) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Role> roleOpt = roleRepository.findByName(request.name());

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();

            Role userRoles = user.getRoles();
            if (!userRoles.equals(role)) {
                user.setRoles(userRoles);
                List<User> users = new ArrayList<>();
                users.add(user);
                role.setUser(users);
                userRepository.save(user);
                roleRepository.save(role);
            }

            return ResponseEntity.ok("Role assigned: " + role.getName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> revokeRole(Integer userId, String roleName) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Optional<Role> roleOpt = roleRepository.findByName(roleName);

            if (roleOpt.isPresent()) {
                Role role = roleOpt.get();
                Role userRoles = user.getRoles();

                if (userRoles.equals(role)) {
                    //user.setRoles();
                    userRepository.save(user);
                    roleRepository.save(role);
                    return ResponseEntity.ok("Role revoked: " + roleName);
                } else {
                    return ResponseEntity.ok("User does not have the role: " + roleName);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
