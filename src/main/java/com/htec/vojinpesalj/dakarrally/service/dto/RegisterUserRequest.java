package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "User information for registering new user")
public class RegisterUserRequest {

    @NotBlank(message = "RegisterUserRequest.name cannot be null or empty.")
    @ApiModelProperty(notes = "Name of the user")
    private String name;

    @NotBlank(message = "RegisterUserRequest.username cannot be null or empty.")
    @ApiModelProperty(notes = "Username of the user")
    private String username;

    @Email
    @NotBlank(message = "RegisterUserRequest.email cannot be null or empty.")
    @ApiModelProperty(notes = "Email of the user")
    private String email;

    @NotBlank(message = "RegisterUserRequest.password cannot be null or empty.")
    @ApiModelProperty(notes = "Password of the user")
    private String password;
}
