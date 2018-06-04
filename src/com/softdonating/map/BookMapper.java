package com.softdonating.map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.softdonating.entity.Book;

import java.util.List;

public interface BookMapper {
    @Select("select * from Book where isbn=#{isbn}")
    public Book findBookById(String isbn);

    public boolean createBook(Book book);

    @Delete("delete from Book where isbn=#{isbn}")
    public boolean deleteBook(String isbn);

    @Select("select name from Book where name LIKE CONCAT('%',#{data},'%')")
    public List<String> findBookName(String data);
}
