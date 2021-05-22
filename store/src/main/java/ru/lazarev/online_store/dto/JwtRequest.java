package ru.lazarev.online_store.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class JwtRequest {
    @ApiModelProperty(notes = "Username", required = true, position = 1, example = "bob1")
    private String username;

    @ApiModelProperty(notes = "Users password ", required = true, position = 2, example = "111")
    private String password;
}
