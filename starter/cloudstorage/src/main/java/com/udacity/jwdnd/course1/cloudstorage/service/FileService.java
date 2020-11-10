package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int add(File file, int userId, MultipartFile data) throws IOException {
        file.setFilename(data.getOriginalFilename());
        file.setData(data.getBytes());
        file.setContentType(data.getContentType());
        file.setUserId(userId);
        return fileMapper.add(file);
    }

    public boolean delete(int id) {
        File file = fileMapper.findById(id);
        if (file != null) {
            fileMapper.delete(id);
            return true;
        }

        return false;
    }

    public File getById(int id) {
        return fileMapper.findById(id);
    }

    public List<File> getAllFilesForUser(int userId) {
        return fileMapper.getAllFilesForUser(userId);
    }

    public int getUserId(int fileId) {
        Integer userId = fileMapper.findById(fileId).getUserId();
        return userId != null ? userId : 0;
    }

}
