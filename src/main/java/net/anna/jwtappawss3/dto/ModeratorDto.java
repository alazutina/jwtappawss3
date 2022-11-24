package net.anna.jwtappawss3.dto;

import net.anna.jwtappawss3.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModeratorDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    public static ModeratorDto fromUser(User user){
        ModeratorDto moderatorDto = new ModeratorDto();
        moderatorDto.setId(user.getId());
        moderatorDto.setUsername(user.getUsername());
        moderatorDto.setFirstName(user.getFirstName());
        moderatorDto.setLastName(user.getLastName());
        moderatorDto.setEmail(user.getEmail());
        return moderatorDto;
    }
}
