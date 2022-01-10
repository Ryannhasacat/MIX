package com.mix.lambda.stream;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionService {
    public Set<String> getPermissions(String department, Long supervisor) {

        Set<String> permissions = new HashSet<>();

        return permissions;
    }
}
