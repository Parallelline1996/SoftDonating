package com.softdonating.map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.softdonating.entity.Follow;

import java.util.List;

public interface FollowMapper {
    public boolean createFollow(Follow follow);

    @Select("select * from Follow where u_ID=#{u_ID}")
    public List<Follow> findFollowsById(String u_ID);

    @Delete("delete from Follow where isbn=#{isbn} and u_ID=#{u_ID}")
    public boolean deleteFollow(Follow follow);
}
