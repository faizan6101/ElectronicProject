package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer userId;

    @Size(min = 3,max = 15,message = "Username must be 3 character!!")
    private String name;

   @Email( message = "Invalid User Email !!")
   @NotBlank(message = "Email is required")
    private String email;

   @NotBlank(message = "password is required !!")
    private String password;

   @Size(min =4,max = 6,message = "Invalid gender !!")
    private String gender;

   @NotBlank(message = "write something about yourself")
    private String about;

    private String imageName;



}
