package net.anna.jwtappawss3.service.impl;

import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.repository.FileRepository;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private  final UserRepository userRepository;
    private final FileRepository fileRepository;
    private  final RoleRepository roleRepository;
    private  final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public FileServiceImpl(UserRepository userRepository, FileRepository fileRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public File register(File file) {
        File file1 = fileRepository.save(file);
        log.info("In register - file {} successfully save ", file1);
        return file1;
    }

    @Override
    public List<File> getAll() {
        List<File> result = fileRepository.findAll();
        log.info("in getAll - {} files found", result.size());
        return result;
    }

    @Override
    public File findByPath(String path) {
        File result = fileRepository.findByPath(path);
        log.info("in findByPath - file {} found by path {} ", result, path);
        return result;
    }

    @Override
    public File findById(Long id) {
        File result = fileRepository.getById(id);//.orElse(null);
        if(result==null){
            log.warn("In findById - no file found by id {}", id);
            return null;
        }
        log.warn("In findById - file {} found by id {}", result,id);
        return result;
    }

    @Override
    public File update(File file) {
        log.warn("In update - file {} updated", file);
        return fileRepository.save(file);
    }

    @Override
    public void delete(Long id) {
    fileRepository.deleteById(id);
    log.info("in delete - file with id {} deleted", id);
    }
}
