package br.com.viceri.todolist.dto;

import br.com.viceri.todolist.types.PriorityEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PartialUpdateTaskDTO {
    private String description;
    private PriorityEnum priority;
}
