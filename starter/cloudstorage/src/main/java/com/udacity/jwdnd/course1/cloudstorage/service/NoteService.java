package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int add(Note note, int userId) {
        note.setUserId(userId);
        return noteMapper.add(note);
    }

    public Note get(int noteId) {
        return noteMapper.get(noteId);
    }


    public int getUserId(int noteId) {
        Note note = noteMapper.get(noteId);
        return note != null ? note.getUserId() : -1;
    }

    public List<Note> getAllNotesForUser(int userId) {
        return noteMapper.getAllNotesForUser(userId);
    }

    public void delete(int noteId) {
        noteMapper.deleteByID(noteId);
    }

    public void update(Note note) {
        noteMapper.update(note);
    }

    public boolean exists(int id) {
        return noteMapper.get(id) != null;
    }
}
