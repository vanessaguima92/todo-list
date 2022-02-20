package br.com.viceri.todolist.dto;

import br.com.viceri.todolist.types.PriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TaskDTO {
    private String description;
    private PriorityEnum priority;
}
