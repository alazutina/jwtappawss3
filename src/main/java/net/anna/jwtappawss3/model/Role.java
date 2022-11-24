package net.anna.jwtappawss3.model;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="roles")
@Data
public class Role extends BaseEntity{

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy ="roles",fetch = FetchType.LAZY)
    private List<User> users;


}
