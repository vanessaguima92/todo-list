package br.com.viceri.todolist.controllers;

import br.com.viceri.todolist.dto.CreateTaskDTO;
import br.com.viceri.todolist.dto.TaskDTO;
import br.com.viceri.todolist.entity.Task;
import br.com.viceri.todolist.exceptions.UserNotFoundException;
import br.com.viceri.todolist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAll(){
        return ResponseEntity.ok(this.taskService.getAll());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody CreateTaskDTO createTaskDTO){
        try{
            TaskDTO taskDTO = this.taskService.create(createTaskDTO);
            return ResponseEntity.ok(taskDTO);
        }
        catch(UserNotFoundException ex){
            System.out.println(String.format("Error while inserting a new task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.badRequest().body(null);
        }
    }
}
