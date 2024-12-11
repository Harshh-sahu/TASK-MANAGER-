package com.zosh.controller;


import com.zosh.modal.Task;
import com.zosh.modal.TaskStatus;
import com.zosh.modal.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> getAssignedUsersTask() {


        return new ResponseEntity<>("Welcome to Task Service", HttpStatus.OK);
    }


}
