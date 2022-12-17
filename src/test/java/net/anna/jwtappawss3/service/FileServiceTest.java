package net.anna.jwtappawss3.service;

import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.repository.FileRepository;
import net.anna.jwtappawss3.repository.RoleRepository;
import net.anna.jwtappawss3.repository.UserRepository;
import net.anna.jwtappawss3.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

public class FileServiceTest {

    @Mock
    FileRepository fileRepository;

    FileService fileService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fileService = new FileServiceImpl( userRepository, fileRepository, roleRepository, passwordEncoder);
    }

    @Test
    public void save() {
        ReflectionTestUtils.setField(fileService, "fileRepository", fileRepository);
        File file = new File();
        file.setPath("11.txt");
        Mockito.when(fileRepository.save(Mockito.any(File.class))).thenReturn(file);
        assertEquals(file.toString(), fileService.register(file).toString());
    }

    @Test
    public void findById() {
        ReflectionTestUtils.setField(fileService, "fileRepository", fileRepository);
        File file = new File();
        file.setPath("11.txt");
        file.setId(9L);
        Mockito.when(fileRepository.getById(9l)).thenReturn(file);
        assertEquals(file, fileService.findById(9l));
    }

    @Test
    public void getAll(){
        ReflectionTestUtils.setField(fileService, "fileRepository", fileRepository);
        List<File> fileEntities = new ArrayList<>();
        File file = new File();
        file.setPath("11.txt");
        fileEntities.add(file);
        Mockito.when(fileRepository.findAll()).thenReturn(fileEntities);
        assertEquals(fileEntities.toString(), fileService.getAll().toString());
    }


    @Test
    public void deleteById() {
        ReflectionTestUtils.setField(fileService, "fileRepository", fileRepository);
        doNothing().when(fileRepository).deleteById(Mockito.anyLong());
        fileService.delete(15l);
        Mockito.verify(fileRepository,Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    public void update() {
        ReflectionTestUtils.setField(fileService, "fileRepository", fileRepository);
        File file = new File();
        file.setPath("11.txt");
        Mockito.when(fileRepository.save(Mockito.any(File.class))).thenReturn(file);
        assertEquals(file.toString(), fileService.update(file).toString());
    }
}
