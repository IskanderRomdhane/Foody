package com.Foody.Foody.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Controller
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
   private final RoleService roleService;
    @PostMapping("/create-role")
    private ResponseEntity<Integer> addRole(
            @RequestBody nRole request){
            return roleService.addRole(request);
    }
    @PutMapping("/assign-role/{id}")
    private ResponseEntity<String> assignRole(
            @PathVariable Integer id ,
            @RequestBody nRole request
    ){
        return roleService.assignRole(id , request);
    }
    @PutMapping("/revoke-role/{id}")
    public ResponseEntity<String> revokeRole(
            @RequestParam Integer id,
            @RequestParam String roleName
            ){
        return roleService.revokeRole(id , roleName);
    }

}