package br.com.viceri.todolist.controllers;

import br.com.viceri.todolist.dto.CreateTaskDTO;
import br.com.viceri.todolist.dto.HttpExceptionCustomResponseDTO;
import br.com.viceri.todolist.dto.PartialUpdateTaskDTO;
import br.com.viceri.todolist.entity.Task;
import br.com.viceri.todolist.exceptions.TaskNotFoundException;
import br.com.viceri.todolist.exceptions.TaskPermissionException;
import br.com.viceri.todolist.exceptions.UserNotFoundException;
import br.com.viceri.todolist.services.TaskService;
import br.com.viceri.todolist.types.PriorityEnum;
import br.com.viceri.todolist.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
@SecurityRequirement(name="to-do-list")
public class TaskController {

    private final TaskService taskService;

    private final JwtTokenUtil jwtTokenUtil;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all tasks successfully.", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAll(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam(value = "priority", required = false) PriorityEnum priorityFilter) {
        final UUID userId = UUID.fromString(this.jwtTokenUtil.getClaimValueFromToken(token, "userId"));

        if(nonNull(priorityFilter)){
            return ResponseEntity.ok(this.taskService.getAll().stream().filter(t -> t.getUser().getId().equals(userId) && t.getPriority().equals(priorityFilter) && !t.isDone()).collect(Collectors.toList()));
        }

        return ResponseEntity.ok(this.taskService.getAll().stream().filter(t -> t.getUser().getId().equals(userId) && !t.isDone()).collect(Collectors.toList()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully.", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Task> create(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody CreateTaskDTO createTaskDTO) {
        try {
            final UUID userId = UUID.fromString(this.jwtTokenUtil.getClaimValueFromToken(token, "userId"));
            Task task = this.taskService.create(createTaskDTO, userId);
            return ResponseEntity.created(null).body(task);
        } catch (UserNotFoundException ex) {
            System.out.println(String.format("Error while inserting a new task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") String id) {
        try {
            final UUID userId = UUID.fromString(this.jwtTokenUtil.getClaimValueFromToken(token, "userId"));
            this.taskService.delete(id, userId);
            return ResponseEntity.ok().body(null);
        } catch (TaskNotFoundException ex) {
            System.out.println(String.format("Error while updating task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.badRequest().body(null);
        } catch (TaskPermissionException ex) {
            System.out.println(String.format("User do not has permission to delete this task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpExceptionCustomResponseDTO("TaskPermissionException", "Usuario não tem permissão para deletar essa tarefa."));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task updated successfully.", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PatchMapping("{id}")
    public ResponseEntity<?> partialUpdate(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody PartialUpdateTaskDTO partialUpdate, @PathVariable("id") String id) {
        try {
            final UUID userId = UUID.fromString(this.jwtTokenUtil.getClaimValueFromToken(token, "userId"));
            Task task = this.taskService.partialUpdate(partialUpdate, id, userId);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException ex) {
            System.out.println(String.format("Error while updating task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.badRequest().body(null);
        } catch (TaskPermissionException ex) {
            System.out.println(String.format("User do not has permission to update the task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpExceptionCustomResponseDTO("TaskPermissionException", "Usuario não tem permissão para deletar essa tarefa."));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully.", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("{id}/done")
    public ResponseEntity done(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") String id) {
        try{
            final UUID userId = UUID.fromString(this.jwtTokenUtil.getClaimValueFromToken(token, "userId"));
            final Task task = this.taskService.setTaskDone(id, userId);
            return ResponseEntity.ok().body(task);
        } catch(TaskNotFoundException ex){
            System.out.println(String.format("Error while updating task. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.notFound().build();
        } catch (TaskPermissionException ex) {
            System.out.println(String.format("User do not has permission to set this task to done. Error: %s", ex.getLocalizedMessage()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpExceptionCustomResponseDTO("TaskPermissionException", "Usuario não tem permissão para deletar essa tarefa."));
        }
    }
}
