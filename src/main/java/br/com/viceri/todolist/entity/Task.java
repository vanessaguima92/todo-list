package br.com.viceri.todolist.entity;

import br.com.viceri.todolist.types.PriorityEnum;
import br.com.viceri.todolist.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity()
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore()
    private User user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated
    private PriorityEnum priority;
}
