package net.anna.jwtappawss3.service;

import net.anna.jwtappawss3.model.File;

import java.util.List;

public interface FileService {
        File register(File file);
        List<File> getAll();
        File findByPath(String path);
        File findById(Long id);
       File update(File file);
        void delete(Long id);

    }

