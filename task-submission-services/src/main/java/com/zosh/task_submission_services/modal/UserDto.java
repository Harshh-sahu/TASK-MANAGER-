package com.zosh.task_submission_services.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {



    private Long id;
    private String password;
    private String email;
    private String role;
    private String fullName;




}
