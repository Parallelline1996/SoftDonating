package com.softdonating.map;

import org.apache.ibatis.annotations.Select;

import com.softdonating.entity.Take;

import java.util.List;

public interface TakeMapper {
    public boolean createTake(Take take);

    @Select("select * from Donate where d_ID=#{d_ID}")
    public List<Take> donateRecord(String d_ID);

    @Select("select * from Donate where u_ID=#{u_ID}")
    public List<Take> takeRecord(String u_ID);
}
