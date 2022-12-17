package net.anna.jwtappawss3.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="events")
@Data
public class Event extends BaseEntity {

//    @Enumerated(EnumType.STRING)
    @Column(name ="action")
    private String action;

    @Column(name="user_id")
    private Long user_id;

    @Column(name="file_id")
    private Long file_id;
}
