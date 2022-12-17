package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Status;
import net.anna.jwtappawss3.security.jwt.AuthenticPrincipal;
import net.anna.jwtappawss3.security.jwt.JwtUser;
import net.anna.jwtappawss3.service.EventService;
import net.anna.jwtappawss3.service.FileService;
import net.anna.jwtappawss3.service.S3Service;
import net.anna.jwtappawss3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class FileRestControllerV1Test {


    @Mock
    UserService userService;

    @Mock
    FileService fileService;

    @Mock
    EventService eventService;

    @Mock
    S3Service s3Service;

    @Mock
    AuthenticPrincipal authenticPrincipal;

    FileRestControllerV1 fileRestControllerV1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fileRestControllerV1 = new FileRestControllerV1(fileService,eventService);
    }

    @Test
    void getAllFiles() {
        ReflectionTestUtils.setField(fileRestControllerV1, "fileService", fileService);
        List<File> arrayList = new ArrayList<>();
        File file = new File();
        file.setPath("11.txt");
        arrayList.add(file);
        Mockito.when(fileService.getAll()).thenReturn(arrayList);
        List<FileDto> fileDtos=new ArrayList<>();
        for( File f : arrayList){
            fileDtos.add(FileDto.fromFile(f));
        }
        ResponseEntity<List<FileDto>> response = new ResponseEntity<>(fileDtos, HttpStatus.OK);
        assertEquals(response, fileRestControllerV1.getAllFiles());
    }

    @Test
    void getFileById() {
        ReflectionTestUtils.setField(fileRestControllerV1, "fileService", fileService);
        File file = new File();
        file.setPath("11.txt");
        Mockito.when(fileService.findById(Mockito.anyLong())).thenReturn(file);
        FileDto result = FileDto.fromFile(file);
        ResponseEntity<FileDto> response = new ResponseEntity<>(result, HttpStatus.OK);
        assertEquals(response, fileRestControllerV1.getFileById(1l));
    }

    @Test
    void deleteFileById() {
        File file = new File();
        file.setPath("11.txt");

        Event e = new Event();
        e.setStatus(Status.DELETED);
        e.setAction("Deleted");
        e.setFile_id(1L);
        e.setUser_id(1L);

        JwtUser jwtUser = new JwtUser(1L,"Anna","Anna","L","1","anna@mail.com",null,true,new Date());
        ResponseEntity responseEntity = new ResponseEntity<>(EventDto.fromEvent(e),HttpStatus.OK);

        ReflectionTestUtils.setField(fileRestControllerV1, "fileService", fileService);
        ReflectionTestUtils.setField(fileRestControllerV1, "s3Service", s3Service);
        ReflectionTestUtils.setField(fileRestControllerV1, "authenticPrincipal", authenticPrincipal);
        ReflectionTestUtils.setField(fileRestControllerV1, "eventService", eventService);

        Mockito.when(fileService.findById(Mockito.anyLong())).thenReturn(file);
        doNothing().when(s3Service).deleteFile(Mockito.anyString());
        Mockito.when(authenticPrincipal.getPrincipal()).thenReturn(jwtUser);
        Mockito.when(eventService.register(Mockito.any(Event.class))).thenReturn(null);

        assertEquals(responseEntity, fileRestControllerV1.deleteFileById(1l));

    }

    @Test
    void save() {
        ReflectionTestUtils.setField(fileRestControllerV1, "fileService", fileService);
        ReflectionTestUtils.setField(fileRestControllerV1, "s3Service", s3Service);
        ReflectionTestUtils.setField(fileRestControllerV1, "authenticPrincipal", authenticPrincipal);
        ReflectionTestUtils.setField(fileRestControllerV1, "eventService", eventService);

        File file = new File();
        file.setPath("11.txt");
        doNothing().when(s3Service).uploadFile(file.getPath());

        Event e = new Event();
        e.setStatus(Status.ACTIVE);
        e.setAction("Uploaded");
        e.setFile_id(1L);
        e.setUser_id(1L);

        JwtUser jwtUser = new JwtUser(1L,"Anna","Anna","L","1","anna@mail.com",null,true,new Date());
        ResponseEntity responseEntity = new ResponseEntity<>(FileDto.fromFile(file),HttpStatus.OK);
        Mockito.when(authenticPrincipal.getPrincipal()).thenReturn(jwtUser);
        Mockito.when(eventService.register(Mockito.any(Event.class))).thenReturn(null);
        Mockito.when(fileService.register(Mockito.any(File.class))).thenReturn(file);

        assertEquals(responseEntity, fileRestControllerV1.save(file));

    }

    @Test
    void update() {
        ReflectionTestUtils.setField(fileRestControllerV1, "fileService", fileService);
        File file = new File();
        file.setPath("11.txt");
        Mockito.when(fileService.update(Mockito.any(File.class))).thenReturn(file);
        FileDto result = FileDto.fromFile(file);
        ResponseEntity<FileDto> response = new ResponseEntity<>(result, HttpStatus.OK);
        assertEquals(response, fileRestControllerV1.update(file));
    }


}