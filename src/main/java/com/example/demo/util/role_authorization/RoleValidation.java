package com.example.demo.util.role_authorization;

import com.example.demo.enums.Role;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.UnAuthorizedException;

public class RoleValidation {

    private RoleValidation() {
        throw new IllegalStateException("Utility class");
    }

    public static void allowRoles(Role userRole, Role... roles) {
        if (userRole == null) {
            throw new UnAuthorizedException("You are not authorized to access this resource");
        }
        for (Role role : roles) {
            if (role.toString().equals(userRole.toString())) {
                return;
            }
        }
        throw new ForbiddenException("You are not allowed to access this resource");
    }   
}
