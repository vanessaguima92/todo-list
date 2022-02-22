package br.com.viceri.todolist.services;

import br.com.viceri.todolist.dto.CreateTaskDTO;
import br.com.viceri.todolist.dto.TaskDTO;
import br.com.viceri.todolist.entity.Task;
import br.com.viceri.todolist.entity.User;
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

    public TaskDTO create(CreateTaskDTO createTaskDTO) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findById(UUID.fromString(createTaskDTO.getIdUsuario()));

        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        var task = new Task();

        task.setDescription(createTaskDTO.getDescription());
        task.setPriority(createTaskDTO.getPriority());

        task.setUser(user.get());

        this.taskRepository.save(task);

        return new TaskDTO(task.getDescription(), task.getPriority());
    }
}
