package com.user.auth.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResDto {

    @ApiModelProperty(example = "-1")
    private String code;
    @ApiModelProperty(example = "Error Message")
    private String message;
}