package net.anna.jwtappawss3.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="files")
@Data
public class File extends BaseEntity {

    @Column(name="path")
    private String path;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="events",
            joinColumns = {@JoinColumn(name ="file_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")})
    private List<User> users;

    @Override
    public String toString() {
        return "File{" +
                "path='" + path + '\'' +
                ", users=" + users +
                "} " + super.toString();
    }
}
