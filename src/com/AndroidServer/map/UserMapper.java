package com.AndroidServer.map;

import com.AndroidServer.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from User where u_ID=#{u_ID}")
    public User findUserById(String u_ID);

    public boolean createUser(User user);
}
