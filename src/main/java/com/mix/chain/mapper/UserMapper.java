package com.mix.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mix.chain.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> findAllUser();
}
