package com.mix.lambda.stream;

import java.util.HashSet;
import java.util.Set;

public class PermissionService {
    public Lazy<Set<String>> getPermissions(String department, Long supervisor) {

        Set<String> permissions = new HashSet<>();

        return Lazy.of(() -> permissions);
    }
}
