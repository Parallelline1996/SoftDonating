package com.softdonating.map;

import org.apache.ibatis.annotations.Select;

import com.softdonating.entity.User;

public interface UserMapper {
    @Select("select * from User where u_ID=#{u_ID}")
    public User findUserById(String u_ID);

    public boolean createUser(User user);
}
