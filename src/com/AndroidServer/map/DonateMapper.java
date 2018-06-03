package com.AndroidServer.map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.AndroidServer.entity.Donate;

import java.util.List;

public interface DonateMapper {
    public boolean createDonate(Donate donate);

    @Update("UPDATE Donate SET\n" +
            "        status = #{status},\n" +
            "        where d_ID = #{d_ID}")
    public boolean confirmDonate(int d_ID, int status);

    @Select("select * from Donate where u_ID=#{u_ID} and status=#{status}")
    public List<Donate> undeterminedDonate(String u_ID, int status);

    @Delete("delete from Donate where u_ID=#{u_ID}")
    public boolean deleteDonate(String u_ID);
}
