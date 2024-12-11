package com.zosh.service;

import com.zosh.Repository.TaskRepository;
import com.zosh.modal.Task;
import com.zosh.modal.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class TaskServiceServiceImplementation implements TasKService{

    @Autowired
       private TaskRepository taskRepository;



    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
       if(!requestRole.equals(("ROLE_ADMIN"))){
           throw new Exception("only admin can create Task");

       }
       task.setStatus(TaskStatus.PENDING);
       task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("TASK NOT FOUND with ID!!"+id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
      List<Task> allTask = taskRepository.findAll();
      List<Task> filterTasks = allTask.stream().filter(
              task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
      ).collect(Collectors.toList());



        return filterTasks ;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {

Task existingTask = getTaskById(id);
if(updatedTask.getTitle()!=null){
    existingTask.setTitle(updatedTask.getTitle());
}


        if(updatedTask.getImage()!=null){
            existingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }

        if(updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }

        if(updatedTask.getDeadLine()!=null){
            existingTask.setDeadLine(updatedTask.getDeadLine());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {

         getTaskById(id);

        taskRepository.deleteById(id);


    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {


        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTasks = taskRepository.findByAssignedUserId(userId);

        List<Task> filterTasks = allTasks.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());



        return filterTasks ;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
