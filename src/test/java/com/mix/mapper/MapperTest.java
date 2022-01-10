package com.mix.mapper;

import com.mix.chain.mapper.UserMapper;
import com.mix.chain.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

//    @Test
    void findUser(){
        List<User> allUsers = userMapper.findAllUser();
        allUsers.forEach((user) -> System.out.println(user.getUserName()));
    }


}
