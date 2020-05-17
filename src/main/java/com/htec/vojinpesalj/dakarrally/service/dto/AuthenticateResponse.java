package com.htec.vojinpesalj.dakarrally.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Login response")
public class AuthenticateResponse {
    @ApiModelProperty(notes = "Type of the token")
    private String type;

    @ApiModelProperty(notes = "Token value")
    private String token;
}
