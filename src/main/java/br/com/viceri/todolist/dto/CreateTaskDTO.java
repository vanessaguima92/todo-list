package br.com.viceri.todolist.dto;

import br.com.viceri.todolist.types.PriorityEnum;
import lombok.Data;

@Data
public class CreateTaskDTO {
    private String description;
    private PriorityEnum priority;
}
