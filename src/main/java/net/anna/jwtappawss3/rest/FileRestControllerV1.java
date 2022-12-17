package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.EventDto;
import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.model.Event;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Status;
import net.anna.jwtappawss3.model.User;
import net.anna.jwtappawss3.security.jwt.AuthenticPrincipal;
import net.anna.jwtappawss3.security.jwt.JwtUser;
import net.anna.jwtappawss3.service.EventService;
import net.anna.jwtappawss3.service.FileService;
import net.anna.jwtappawss3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/files/")
public class FileRestControllerV1 {

        private final FileService fileService;
        private final EventService eventService;

        AuthenticPrincipal authenticPrincipal;

        @Autowired
        private S3Service s3Service;

        @Autowired
        public FileRestControllerV1(FileService fileService, EventService eventService) {
            this.fileService = fileService;
            this.eventService = eventService;
            this.authenticPrincipal = new AuthenticPrincipal();
        }

        @GetMapping(value = "")
        public ResponseEntity<List<FileDto>> getAllFiles() {
            List<File> arrayList = fileService.getAll();
            if(arrayList==null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<FileDto> fileDtos=new ArrayList<>();
            for( File f : arrayList){

            fileDtos.add(FileDto.fromFile(f));
            }
            return new ResponseEntity<>(fileDtos,HttpStatus.OK);
        }

    @GetMapping(value = "{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable(name = "id") Long id){
            File file = fileService.findById(id);
        if(file==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        FileDto result = FileDto.fromFile(file);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<EventDto> deleteFileById(@PathVariable(name = "id") Long id)
    {
        File file = fileService.findById(id);
        String name = file.getPath();
        s3Service.deleteFile(name);

        file.setStatus(Status.DELETED);
        Event e = new Event();

        e.setStatus(Status.DELETED);
        e.setAction("Deleted");
        e.setFile_id(id);

        JwtUser principal = authenticPrincipal.getPrincipal();

//        String username = principal.getUsername();
//        System.out.println("1"+username+" "+ principal.getId());

        e.setUser_id(principal.getId());
        eventService.register(e);
        return new ResponseEntity<>(EventDto.fromEvent(e),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<FileDto> save(@RequestBody File object) {

        s3Service.uploadFile(object.getPath());
        object.setStatus(Status.ACTIVE);
        File file1 =   fileService.register(object);
        FileDto result = FileDto.fromFile(file1);

        Event event = new Event();
        event.setStatus(Status.ACTIVE);
        event.setFile_id(file1.getId());
        event.setAction("Uploaded");

        JwtUser principal = authenticPrincipal.getPrincipal();

        String username = principal.getUsername();
        event.setUser_id(principal.getId());
        eventService.register(event);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<FileDto> update(@RequestBody File object) {
        File file1 =   fileService.update(object);
        FileDto result = FileDto.fromFile(file1);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}


