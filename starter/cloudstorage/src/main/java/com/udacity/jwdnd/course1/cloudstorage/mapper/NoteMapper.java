package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoteMapper {

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int add(Note note);


    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getAllNotesForUser(int userId);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    void deleteByID(int noteId);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    public void update(Note note);

    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Note get(int noteId);
}
