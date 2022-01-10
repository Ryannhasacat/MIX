package com.mix.lambda.stream;

import org.springframework.stereotype.Component;

@Component
public class DepartmentService {

    public <T> T getDepartment(long uid) {
        return (T) new String("123456");
    }
}
