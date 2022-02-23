package br.com.viceri.todolist.services;

import br.com.viceri.todolist.dto.CreateTaskDTO;
import br.com.viceri.todolist.dto.PartialUpdateTaskDTO;
import br.com.viceri.todolist.entity.Task;
import br.com.viceri.todolist.entity.User;
import br.com.viceri.todolist.exceptions.TaskNotFoundException;
import br.com.viceri.todolist.exceptions.TaskPermissionException;
import br.com.viceri.todolist.exceptions.UserNotFoundException;
import br.com.viceri.todolist.repository.TaskRepository;
import br.com.viceri.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getAll(){
        return this.taskRepository.findAll();
    }

    public Task create(CreateTaskDTO createTaskDTO, UUID userId) throws UserNotFoundException {
        Optional<User> user = getUser(userId);
        var task = new Task();

        task.setDescription(createTaskDTO.getDescription());
        task.setPriority(createTaskDTO.getPriority());

        task.setUser(user.get());

        final Task savedTask = this.taskRepository.save(task);
        return savedTask;
    }

    public void delete(String id, UUID userId) throws TaskNotFoundException, TaskPermissionException {
        final Optional<Task> task = this.taskRepository.findById(UUID.fromString(id));

        if(task.isEmpty()){
            throw new TaskNotFoundException();
        }

        if(!task.get().getUser().getId().equals(userId)){
            throw new TaskPermissionException();
        }

        this.taskRepository.deleteById(UUID.fromString(id));
    }

    private Optional<User> getUser(UUID id) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user;
    }

    public Task setTaskDone(String id, UUID userId) throws TaskNotFoundException, TaskPermissionException {
        final Task taskToUpdate = getTask(id, userId);
        taskToUpdate.setDone(true);
        final Task savedTask = this.taskRepository.save(taskToUpdate);
        return savedTask;
    }

    public Task partialUpdate(PartialUpdateTaskDTO partialUpdate, String id, UUID userId) throws TaskNotFoundException, TaskPermissionException {
        final Task taskToUpdate = getTask(id, userId);
        taskToUpdate.setDescription(partialUpdate.getDescription());
        taskToUpdate.setPriority(partialUpdate.getPriority());

        final Task savedTask = this.taskRepository.save(taskToUpdate);
        return savedTask;
    }

    private Task getTask(String id, UUID userId) throws TaskNotFoundException, TaskPermissionException {
        final Optional<Task> task = this.taskRepository.findById(UUID.fromString(id));

        if (task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        if (!task.get().getUser().getId().equals(userId)) {
            throw new TaskPermissionException();
        }

        return task.get();
    }
}
