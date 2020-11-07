package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CredentialMapper {

    @Insert("INSERT INTO credentials (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int add(Credential credential);

    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getAllCredentialsForUser(int userId);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialId}")
    Credential getById(int credentialId);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    void delete(int credentialsId);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialId}")
    public void update(Credential credential);
}
