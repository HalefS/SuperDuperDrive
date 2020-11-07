package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Insert("INSERT INTO users (username, firstname, lastname, salt, password) values (#{username}, #{firstName}, #{lastName}, #{salt}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int createUser(User user);

    @Select("Select * FROM users WHERE userid = #{userId}")
    User getById(int userId);

    @Select("select * FROM users WHERE username = #{username}")
    User getByUsername(String username);

    @Delete("DELETE FROM users WHERE id = #{userId}")
    void deleteById(int id);

    @Update("UPDATE users SET username = #{username}, lastname = #{lastName}, firstname = {firstName}, salt = #{salt}, password = #{password} WHERE userid = #{userId}")
    void update(User user);
}
