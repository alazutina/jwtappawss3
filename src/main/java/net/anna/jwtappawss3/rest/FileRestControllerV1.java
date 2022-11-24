package net.anna.jwtappawss3.rest;

import net.anna.jwtappawss3.dto.FileDto;
import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.Status;
import net.anna.jwtappawss3.service.FileService;
import net.anna.jwtappawss3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/files/")
public class FileRestControllerV1 {

        private final FileService fileService;
    @Autowired
    private S3Service s3Service;

        @Autowired
        public FileRestControllerV1(FileService fileService) {
            this.fileService = fileService;
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
    public ResponseEntity<FileDto> deleteFileById(@PathVariable(name = "id") Long id)
    {
        File file = fileService.findById(id);
        String name = file.getPath();
        s3Service.deleteFile(name);
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<FileDto> save(@RequestBody File object) {
       // System.out.println(object+"!");
        s3Service.uploadFile(object.getPath());
        object.setStatus(Status.ACTIVE);
        File file1 =   fileService.register(object);
        FileDto result = FileDto.fromFile(file1);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<FileDto> update(@RequestBody File object) {
        File file1 =   fileService.update(object);
        FileDto result = FileDto.fromFile(file1);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}


