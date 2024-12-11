package com.zosh.service;

import com.zosh.modal.Task;
import com.zosh.modal.TaskStatus;

import java.util.List;

public interface TasKService {
    Task createTask(Task task,String requestRole)throws Exception;



    Task getTaskById(Long id)throws Exception;

    List<Task> getAllTask(TaskStatus status);

    Task updateTask(Long id,Task updatedTask,Long userId)throws Exception;


    void deleteTask(Long id) throws Exception;

    Task assignedToUser(Long userId,Long taskId)throws Exception;

    List<Task>assignedUsersTask(Long userId,TaskStatus status);

    Task completeTask(Long taskId)throws Exception;







}
