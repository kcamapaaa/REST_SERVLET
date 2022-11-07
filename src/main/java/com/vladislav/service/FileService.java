package com.vladislav.service;

import com.vladislav.model.File;
import com.vladislav.repository.FileRepository;
import com.vladislav.repository.hibernate.HiberFileRepository;

import java.util.List;

public class FileService {
    private final FileRepository fileRepository = new HiberFileRepository();

    public File getFileById(int id) {
        return fileRepository.getById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.getAll();
    }

    public File addNewFile(File file) {
        return fileRepository.save(file);
    }

    public File updateFile(File file) {
        return fileRepository.update(file);
    }

    public boolean deleteFileById(int id) {
        return fileRepository.deleteById(id);
    }
}
