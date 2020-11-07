package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileMapper {

    @Insert("INSERT INTO files (filename, contentType, userId, data) values (#{filename}, #{contentType}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int add(File file);

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    public File findById(int fileId);

    @Select("SELECT * FROM files WHERE userId = #{userId}")
    List<File> getAllFilesForUser(int userId);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    void delete(int fileId);

}
