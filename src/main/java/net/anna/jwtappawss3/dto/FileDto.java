package net.anna.jwtappawss3.dto;

import net.anna.jwtappawss3.model.File;
import net.anna.jwtappawss3.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {
    private String path;

    public File toFile(){
        File file=new File();
        file.setPath(path);
        return file               ;
    }

    public static FileDto fromFile(File file){
        FileDto fileDto = new FileDto();
        fileDto.setPath(file.getPath());
        return fileDto;
    }


}
